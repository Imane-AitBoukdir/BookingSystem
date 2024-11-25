package com.ensam.hotelalrbadr.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // For auto-increment
    @Column(name = "room_id")
    private Integer roomId; // Primary key

    @Column(name = "room_name", nullable = false, length = 50)
    private String roomName;

    @Column(name = "price_per_night", nullable = false, precision = 7, scale = 2)
    private Double pricePerNight; // Updated to match the decimal(7,2)

    @Column(name = "room_image", columnDefinition = "TEXT")
    private String roomImage;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "availability_status", nullable = false)
    private Boolean availabilityStatus; // Maps to tinyint(1)

    @Column(name = "room_type", nullable = false, length = 30)
    private String roomType;

    @Column(name = "guest_number", nullable = false)
    private Integer guestNumber;

    // Default constructor
    public rooms() {
    }

    // Getters and Setters
    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(String roomImage) {
        this.roomImage = roomImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(Boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getGuestNumber() {
        return guestNumber;
    }

    public void setGuestNumber(Integer guestNumber) {
        this.guestNumber = guestNumber;
    }
}
