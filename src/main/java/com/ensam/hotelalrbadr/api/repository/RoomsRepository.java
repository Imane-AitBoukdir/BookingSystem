package com.ensam.hotelalrbadr.api.repository;

import com.ensam.hotelalrbadr.api.model.rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomsRepository extends JpaRepository<rooms, Integer> {
    // You can define custom queries here if needed
}
