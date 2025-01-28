package com.ensam.hotelalrbadr.api.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * This class represents a hotel room reservation.
 * It exactly matches the reservations table in the database.
 */
public class Reservation {
    // Database table columns (exactly as they appear in the table)
    private Integer reservation_id;    // Primary key
    private Integer user_id;           // Reference to users table
    private Integer room_id;           // Reference to rooms table
    private Date check_in_date;        // When the guest arrives (DATE in database)
    private Date check_out_date;       // When the guest leaves (DATE in database)
    private double total_price;        // Total cost of the stay
    private String status;             // Reservation status (PENDING, CONFIRMED, etc)
    private Timestamp created_at;      // When the reservation was created (TIMESTAMP in database)

    // Additional room information (from joining with rooms table)
    private String room_name;          // Name of the room
    private String room_image;         // Room image path
    private String description;        // Room description

    // Possible status values
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_COMPLETED = "COMPLETED";

    /**
     * Default constructor
     */
    public Reservation() {
        this.status = STATUS_PENDING;  // New reservations start as pending
        this.created_at = new Timestamp(System.currentTimeMillis());  // Set current time
    }

    /**
     * Constructor with main reservation details
     */
    public Reservation(Integer user_id, Integer room_id, Date check_in_date,
                       Date check_out_date, double total_price) {
        this();  // Call default constructor to set status and created_at
        this.user_id = user_id;
        this.room_id = room_id;
        this.check_in_date = check_in_date;
        this.check_out_date = check_out_date;
        this.total_price = total_price;
    }

    // Getters and Setters

    public Integer getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Integer reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        if (user_id == null) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        this.user_id = user_id;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        if (room_id == null) {
            throw new IllegalArgumentException("Room ID cannot be empty");
        }
        this.room_id = room_id;
    }

    public Date getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(Date check_in_date) {
        if (check_in_date == null) {
            throw new IllegalArgumentException("Check-in date cannot be empty");
        }
        this.check_in_date = check_in_date;
        validateDates();  // Make sure check-out date is after check-in
    }

    public Date getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(Date check_out_date) {
        if (check_out_date == null) {
            throw new IllegalArgumentException("Check-out date cannot be empty");
        }
        this.check_out_date = check_out_date;
        validateDates();  // Make sure check-out date is after check-in
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        if (total_price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
        String upperStatus = status.toUpperCase().trim();
        if (!isValidStatus(upperStatus)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        this.status = upperStatus;
    }

    /**
     * Gets when this reservation was created
     */
    public Timestamp getCreated_at() {
        return created_at;
    }

    /**
     * Sets when this reservation was created
     * Note: This is usually set automatically by the database,
     * but we include it for completeness
     */
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    /**
     * Gets the creation time as a LocalDateTime (easier to work with)
     */
    public LocalDateTime getCreationDateTime() {
        return created_at != null ? created_at.toLocalDateTime() : null;
    }

    // Room details getters and setters

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_image() {
        return room_image;
    }

    public void setRoom_image(String room_image) {
        this.room_image = room_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Helper methods

    /**
     * Makes sure check-out date is after check-in date
     */
    private void validateDates() {
        if (check_in_date != null && check_out_date != null) {
            if (check_out_date.before(check_in_date)) {
                throw new IllegalArgumentException("Check-out date must be after check-in date");
            }
        }
    }

    /**
     * Checks if a status value is valid
     */
    private boolean isValidStatus(String status) {
        return STATUS_PENDING.equals(status) ||
                STATUS_CONFIRMED.equals(status) ||
                STATUS_CANCELLED.equals(status) ||
                STATUS_COMPLETED.equals(status);
    }

    /**
     * Calculates how many nights the reservation is for
     */
    public long getNumberOfNights() {
        if (check_in_date == null || check_out_date == null) {
            return 0;
        }
        LocalDate start = check_in_date.toLocalDate();
        LocalDate end = check_out_date.toLocalDate();
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * Checks if the reservation is active (not cancelled or completed)
     */
    public boolean isActive() {
        return STATUS_CONFIRMED.equals(status) || STATUS_PENDING.equals(status);
    }

    /**
     * Checks if the reservation can be cancelled
     */
    public boolean canBeCancelled() {
        return isActive() && check_in_date.after(new Date(System.currentTimeMillis()));
    }

    /**
     * Checks if this is an upcoming reservation
     */
    public boolean isUpcoming() {
        return isActive() && check_in_date.after(new Date(System.currentTimeMillis()));
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservation_id=" + reservation_id +
                ", user_id=" + user_id +
                ", room_id=" + room_id +
                ", check_in_date=" + check_in_date +
                ", check_out_date=" + check_out_date +
                ", total_price=" + total_price +
                ", status='" + status + '\'' +
                ", created_at=" + created_at +
                ", room_name='" + room_name + '\'' +
                '}';
    }
}