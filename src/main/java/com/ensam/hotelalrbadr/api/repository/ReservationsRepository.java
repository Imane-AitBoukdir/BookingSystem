package com.ensam.hotelalrbadr.api.repository;

import com.ensam.hotelalrbadr.api.model.reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepository extends JpaRepository<reservations, Integer> {
    // Custom queries can be added here if needed
}
