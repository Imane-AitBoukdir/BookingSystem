package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.model.roomServices;
import com.ensam.hotelalrbadr.api.repository.RoomServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roomservices")
public class roomServicesController {

    @Autowired
    private RoomServicesRepository roomServicesRepository;

    // Get all room services
    @GetMapping
    public List<roomServices> getAllRoomServices() {
        return roomServicesRepository.findAll();
    }

    // Get room service by room_id and service_id
    @GetMapping("/{room_id}/{service_id}")
    public ResponseEntity<roomServices> getRoomServiceById(@PathVariable("room_id") Integer room_id,
                                                           @PathVariable("service_id") Integer service_id) {
        Optional<roomServices> roomService = roomServicesRepository.findById(new roomServices(room_id, service_id));
        return roomService.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new room service
    @PostMapping
    public ResponseEntity<roomServices> createRoomService(@RequestBody roomServices roomServices) {
        roomServices savedRoomService = roomServicesRepository.save(roomServices);
        return new ResponseEntity<>(savedRoomService, HttpStatus.CREATED);
    }

    // Delete a room service by room_id and service_id
    @DeleteMapping("/{room_id}/{service_id}")
    public ResponseEntity<Void> deleteRoomService(@PathVariable("room_id") Integer room_id,
                                                  @PathVariable("service_id") Integer service_id) {
        Optional<roomServices> roomService = roomServicesRepository.findById(new roomServices(room_id, service_id));
        if (roomService.isPresent()) {
            roomServicesRepository.delete(roomService.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
