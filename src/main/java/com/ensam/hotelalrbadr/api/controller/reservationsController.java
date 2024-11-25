package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.model.reservations;
import com.ensam.hotelalrbadr.api.repository.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class reservationsController {

    @Autowired
    private ReservationsRepository reservationsRepository;

    // Get all reservations
    @GetMapping
    public List<reservations> getAllReservations() {
        return reservationsRepository.findAll();
    }

    // Get reservation by ID
    @GetMapping("/{reservation_id}")
    public ResponseEntity<reservations> getReservationById(@PathVariable("reservation_id") Integer id) {
        Optional<reservations> reservation = reservationsRepository.findById(id);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new reservation
    @PostMapping
    public ResponseEntity<reservations> createReservation(@RequestBody reservations reservation) {
        reservation.setReservationDate(java.time.LocalDateTime.now()); // Set reservation date as current time
        reservations savedReservation = reservationsRepository.save(reservation);
        return new ResponseEntity<>(savedReservation, HttpStatus.CREATED);
    }

    // Update an existing reservation
    @PutMapping("/{reservation_id}")
    public ResponseEntity<reservations> updateReservation(@PathVariable("reservation_id") Integer id, @RequestBody reservations reservationDetails) {
        if (!reservationsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reservationDetails.setReservationId(id);
        reservations updatedReservation = reservationsRepository.save(reservationDetails);
        return ResponseEntity.ok(updatedReservation);
    }

    // Delete a reservation by ID
    @DeleteMapping("/{reservation_id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservation_id") Integer id) {
        if (!reservationsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reservationsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
