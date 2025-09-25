pipeline {
    agent any

    stages {

        // ===== FRONTEND BUILD =====
        stage('Build Frontend') {
            steps {
                dir('TVAPI-REACT') {
                    sh '''
                        npm install
                        npm run build
                    '''
                }
            }
        }

        // ===== FRONTEND DEPLOY =====
        stage('Deploy Frontend to Tomcat') {
            steps {
                sh '''
                rm -rf /Users/vamsikrishnareddymallidi/Downloads/apache-tomcat-10.1.43/webapps/reacttvapi
                mkdir -p /Users/vamsikrishnareddymallidi/Downloads/apache-tomcat-10.1.43/webapps/reacttvapi
                cp -R TVAPI-REACT/dist/* /Users/vamsikrishnareddymallidi/Downloads/apache-tomcat-10.1.43/webapps/reacttvapi/
                '''
            }
        }

        // ===== BACKEND BUILD =====
        stage('Build Backend') {
            steps {
                dir('TVAPI-SPRINGBOOT') {
                    sh 'mvn clean package'
                }
            }
        }

        // ===== BACKEND DEPLOY =====
        stage('Deploy Backend to Tomcat') {
            steps {
                sh '''
                rm -f /Users/vamsikrishnareddymallidi/Downloads/apache-tomcat-10.1.43/webapps/springboottvapi.war
                rm -rf /Users/vamsikrishnareddymallidi/Downloads/apache-tomcat-10.1.43/webapps/springboottvapi
                cp TVAPI-SPRINGBOOT/target/*.war /Users/vamsikrishnareddymallidi/Downloads/apache-tomcat-10.1.43/webapps/
                '''
            }
        }
    }
}
