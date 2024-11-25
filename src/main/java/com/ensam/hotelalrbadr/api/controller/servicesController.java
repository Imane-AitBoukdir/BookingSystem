package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.model.services;
import com.ensam.hotelalrbadr.api.repository.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
public class servicesController {

    @Autowired
    private ServicesRepository servicesRepository;

    // Get all services
    @GetMapping
    public List<services> getAllServices() {
        return servicesRepository.findAll();
    }

    // Get service by service_id
    @GetMapping("/{service_id}")
    public ResponseEntity<services> getServiceById(@PathVariable("service_id") Integer service_id) {
        Optional<services> service = servicesRepository.findById(service_id);
        return service.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new service
    @PostMapping
    public ResponseEntity<services> createService(@RequestBody services service) {
        services savedService = servicesRepository.save(service);
        return new ResponseEntity<>(savedService, HttpStatus.CREATED);
    }

    // Delete a service by service_id
    @DeleteMapping("/{service_id}")
    public ResponseEntity<Void> deleteService(@PathVariable("service_id") Integer service_id) {
        Optional<services> service = servicesRepository.findById(service_id);
        if (service.isPresent()) {
            servicesRepository.delete(service.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
