package com.ensam.hotelalrbadr.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    @Column(name = "reservation_id")
    private Integer reservationId; // Primary key

    @Column(name = "user_id", nullable = false)
    private Integer userId; // Foreign key to User (if applicable)

    @Column(name = "room_id", nullable = false)
    private Integer roomId; // Foreign key to Room (if applicable)

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate; // Maps to date type

    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate; // Maps to date type

    @Column(name = "total_price", nullable = false, precision = 10, scale = 3)
    private Double totalPrice; // Maps to decimal(10,3)

    @Column(name = "status", length = 20)
    private String status; // Maps to varchar(20)

    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate; // Maps to timestamp

    // Default constructor
    public reservations() {
    }

    // Getters and Setters
    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }
}
