package com.ensam.hotelalrbadr.api.service;

import java.time.LocalDate;

/**
 * This class stores the check-in and check-out dates that users select.
 * It uses the Singleton pattern so we can access these dates from anywhere in our app.
 */
public class BookingManager {
    // The single instance of this class that will be shared
    private static BookingManager instance;

    // The dates we want to store
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    // Private constructor so no one else can create instances
    private BookingManager() {}

    // Method to get the single instance
    public static BookingManager getInstance() {
        if (instance == null) {
            instance = new BookingManager();
        }
        return instance;
    }

    // Save both dates
    public void setBookingDates(LocalDate checkIn, LocalDate checkOut) {
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
    }

    // Get check-in date
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    // Get check-out date
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    // Clear the dates (useful after booking is complete)
    public void clearDates() {
        checkInDate = null;
        checkOutDate = null;
    }
}