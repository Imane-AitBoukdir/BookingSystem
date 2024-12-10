package com.ensam.hotelalrbadr.api.model;

import java.util.List;

public class Room {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String category;
    private List<Services> services;  // Updated to use new Services class

    // Default constructor
    public Room() {}

    // Parameterized constructor
    public Room(Long id, String name, String description, double price, String imageUrl, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<Services> getServices() { return services; }
    public void setServices(List<Services> services) { this.services = services; }
}