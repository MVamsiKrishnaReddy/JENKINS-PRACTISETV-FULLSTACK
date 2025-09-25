pipeline {
  agent any

  tools {
    maven 'MAVEN'    // Use the Maven installation you configured in Jenkins
  }

  environment {
    HOME              = "${env.HOME}"
    NVM_DIR           = "${env.HOME}/.nvm"
    NODE_VERSION      = "v22.16.0"
    NODE_BIN          = "${env.HOME}/.nvm/versions/node/v22.16.0/bin"
    TOMCAT_HOME       = "/Users/vamsikrishnareddymallidi/Downloads/apache-tomcat-10.1.43"
    PATH              = "${env.PATH}:${env.NODE_BIN}"
  }

  stages {

    stage('Show Versions') {
      steps {
        sh '''
          set -e
          echo "HOME=$HOME"
          echo "Using PATH=$PATH"

          # Load Node via nvm
          if [ -s "$NVM_DIR/nvm.sh" ]; then
            . "$NVM_DIR/nvm.sh"
            nvm use "$NODE_VERSION" >/dev/null
          fi

          which node || true
          which npm || true
          node -v || true
          npm -v || true
          mvn -version || true
        '''
      }
    }

    // ===== FRONTEND BUILD =====
    stage('Build Frontend') {
      steps {
        dir('TVAPI-REACT') {
          sh '''
            set -e
            if [ -s "$NVM_DIR/nvm.sh" ]; then
              . "$NVM_DIR/nvm.sh"
              nvm use "$NODE_VERSION" >/dev/null
            fi
            if [ -f package-lock.json ]; then
              npm ci
            else
              npm install
            fi
            npm run build
          '''
        }
      }
    }

    // ===== FRONTEND DEPLOY =====
    stage('Deploy Frontend to Tomcat') {
      steps {
        sh '''
          set -e
          TARGET="${TOMCAT_HOME}/webapps/reacttvapi"
          rm -rf "$TARGET"
          mkdir -p "$TARGET"
          cp -R TVAPI-REACT/dist/* "$TARGET/"
          echo "Frontend deployed to $TARGET"
        '''
      }
    }

    // ===== BACKEND BUILD =====
    stage('Build Backend') {
      steps {
        dir('TVAPI-SPRINGBOOT') {
          sh '''
            set -e
            mvn -Dmaven.test.skip=true clean package
          '''
        }
      }
    }

    // ===== BACKEND DEPLOY =====
    stage('Deploy Backend to Tomcat') {
      steps {
        sh '''
          set -e
          TOMCAT_WEBAPPS="${TOMCAT_HOME}/webapps"

          rm -f "${TOMCAT_WEBAPPS}/springboottvapi.war" || true
          rm -rf "${TOMCAT_WEBAPPS}/springboottvapi" || true

          WAR_FILE=$(ls TVAPI-SPRINGBOOT/target/*.war | head -n 1)
          if [ -z "$WAR_FILE" ]; then
            echo "ERROR: No WAR found in TVAPI-SPRINGBOOT/target/"
            exit 1
          fi

          cp "$WAR_FILE" "${TOMCAT_WEBAPPS}/"
          echo "Backend WAR deployed to ${TOMCAT_WEBAPPS}/"
        '''
      }
    }

    // ===== RESTART TOMCAT =====
    stage('Restart Tomcat') {
      steps {
        sh '''
          set -e
          if [ -x "${TOMCAT_HOME}/bin/shutdown.sh" ]; then
            "${TOMCAT_HOME}/bin/shutdown.sh" || true
          fi
          sleep 3
          "${TOMCAT_HOME}/bin/startup.sh"
          echo "Tomcat restarted."
        '''
      }
    }
  }

  post {
    success {
      echo 'Deployment Successful!'
    }
    failure {
      echo 'Pipeline Failed.'
    }
  }
}
