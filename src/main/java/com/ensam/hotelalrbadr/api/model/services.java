package com.ensam.hotelalrbadr.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "services")
public class services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Integer serviceId; // Primary key

    @Column(name = "service_name", length = 30, nullable = false)
    private String serviceName;

    // Default constructor
    public services() {
    }

    // Getters and Setters
    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
