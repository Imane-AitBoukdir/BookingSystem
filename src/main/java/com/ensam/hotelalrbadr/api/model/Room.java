package com.ensam.hotelalrbadr.api.model;

import java.sql.Timestamp;

public class Room {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String list_view_image;
    private String category;
    private Timestamp created_at;    // Changed to sql.Timestamp
    private String detail_view_image;
    private String reservation_image;

    // Default constructor
    public Room() {}

    // Constructor with all fields
    public Room(Long id, String name, String description, double price,
                String list_view_image, String category, Timestamp created_at,
                String detail_view_image, String reservation_image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.list_view_image = list_view_image;
        this.category = category;
        this.created_at = created_at;
        this.detail_view_image = detail_view_image;
        this.reservation_image = reservation_image;
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

    public String getListViewImage() { return list_view_image; }
    public void setListViewImage(String list_view_image) { this.list_view_image = list_view_image; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Timestamp getCreatedAt() { return created_at; }
    public void setCreatedAt(Timestamp created_at) { this.created_at = created_at; }

    // In Room.java
    public String getDetailViewImage() {
        return detail_view_image != null ? detail_view_image : "/homePageImages/details/default-detail-room.png";
    }
    public void setDetailViewImage(String detail_view_image) { this.detail_view_image = detail_view_image; }

    public String getReservationImage() { return reservation_image; }
    public void setReservationImage(String reservation_image) { this.reservation_image = reservation_image; }

    // Helper methods for getting complete image paths
    public String getListViewImagePath() {
        return list_view_image != null ? list_view_image : "/homePageImages/list/default.png";
    }

    public String getDetailViewImagePath() {
        return detail_view_image != null ? detail_view_image : "/homePageImages/details/default.png";
    }

    public String getReservationImagePath() {
        return reservation_image != null ? reservation_image : "/homePageImages/reservation/default.png";
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}