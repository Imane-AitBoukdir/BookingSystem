package com.ensam.hotelalrbadr.api.repository;

import com.ensam.hotelalrbadr.api.model.roomServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomServicesRepository extends JpaRepository<roomServices, Integer> {
    Optional<roomServices> findById(roomServices roomServices);
    // Custom query methods can be added here if necessary
}
