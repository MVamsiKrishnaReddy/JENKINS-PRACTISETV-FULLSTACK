package com.klef.dev.model;

import jakarta.persistence.*;

@Entity
public class TV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String brand;
    private String model;
    private String screenSize;
    private String resolution;
    private String smart; // Yes/No
    private double price;

    // Constructors
    public TV() {}

    public TV(int id, String brand, String model, String screenSize, String resolution, String smart, double price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.screenSize = screenSize;
        this.resolution = resolution;
        this.smart = smart;
        this.price = price;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getScreenSize() { return screenSize; }
    public void setScreenSize(String screenSize) { this.screenSize = screenSize; }

    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }

    public String getSmart() { return smart; }
    public void setSmart(String smart) { this.smart = smart; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
