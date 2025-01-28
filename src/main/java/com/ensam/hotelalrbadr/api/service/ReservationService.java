package com.ensam.hotelalrbadr.api.service;

import com.ensam.hotelalrbadr.api.model.Reservation;
import com.ensam.hotelalrbadr.api.repository.ReservationRepository;
import java.util.List;
import java.sql.Date;

/**
 * Service class that handles all reservation-related operations.
 * This class adds business logic and validation between controllers
 * and the repository.
 */
public class ReservationService {
    // Repository to handle database operations
    private final ReservationRepository reservationRepository;

    /**
     * Creates a new ReservationService
     */
    public ReservationService() {
        this.reservationRepository = new ReservationRepository();
    }

    /**
     * Gets all reservations for a specific user
     *
     * @param userId ID of the user
     * @return List of user's reservations
     */
    public List<Reservation> getReservationsForUser(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    /**
     * Creates a new reservation after validating all details
     *
     * @param reservation The reservation to create
     * @throws IllegalArgumentException if reservation details are invalid
     */
    public void createReservation(Reservation reservation) {
        // First validate the reservation
        validateReservation(reservation);

        // Set initial status
        reservation.setStatus(Reservation.STATUS_CONFIRMED);

        // Save to database
        reservationRepository.save(reservation);
    }

    /**
     * Updates an existing reservation
     *
     * @param reservation The reservation with updated details
     * @throws IllegalArgumentException if changes make the reservation invalid
     */
    public void updateReservation(Reservation reservation) {
        if (isValidReservation(reservation)) {
            reservationRepository.update(reservation);
        } else {
            throw new IllegalArgumentException("Invalid reservation details");
        }
    }

    /**
     * Gets all reservations for a specific user by ID
     *
     * @param userId ID of the user
     * @return List of reservations
     */
    public List<Reservation> getReservationById(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    /**
     * Validates all aspects of a reservation before saving
     */
    private void validateReservation(Reservation reservation) {
        // Check if reservation exists
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }

        // Check if user ID is set
        if (reservation.getUser_id() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        // Check if room ID is set
        if (reservation.getRoom_id() == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }

        // Validate dates and price
        if (!isValidReservation(reservation)) {
            throw new IllegalArgumentException("Invalid reservation dates or price");
        }
    }

    /**
     * Checks if a reservation's dates and price are valid
     */
    private boolean isValidReservation(Reservation reservation) {
        // Check if we have all required data
        if (reservation.getCheck_in_date() == null ||
                reservation.getCheck_out_date() == null ||
                reservation.getTotal_price() <= 0) {
            return false;
        }

        // Get current date
        Date currentDate = new Date(System.currentTimeMillis());
        Date checkIn = reservation.getCheck_in_date();
        Date checkOut = reservation.getCheck_out_date();

        // Make sure check-in isn't in the past
        if (checkIn.before(currentDate)) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }

        // Make sure check-out is after check-in
        return !checkOut.before(checkIn);
    }
}