package com.ensam.hotelalrbadr.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roomservices")
public class roomServices {

    @Id
    @Column(name = "room_id")
    private Integer roomId; // Primary key part 1

    @Id
    @Column(name = "service_id")
    private Integer serviceId; // Primary key part 2

    // Default constructor
    public roomServices() {
    }

    public roomServices(Integer roomId, Integer serviceId) {
    }

    // Getters and Setters
    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
}
