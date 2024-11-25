package com.ensam.hotelalrbadr.api.repository;

import com.ensam.hotelalrbadr.api.model.services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicesRepository extends JpaRepository<services, Integer> {
    // Custom query methods can be added here if necessary
}
