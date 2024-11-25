package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.model.rooms;
import com.ensam.hotelalrbadr.api.repository.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class roomsController {

    @Autowired
    private RoomsRepository roomsRepository;

    // Get all rooms
    @GetMapping
    public List<rooms> getAllRooms() {
        return roomsRepository.findAll();
    }

    // Get room by ID
    @GetMapping("/{room_id}")
    public ResponseEntity<rooms> getRoomById(@PathVariable("room_id") Integer id) {
        Optional<rooms> room = roomsRepository.findById(id);
        return room.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new room
    @PostMapping
    public ResponseEntity<rooms> createRoom(@RequestBody rooms room) {
        rooms savedRoom = roomsRepository.save(room);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    // Update an existing room
    @PutMapping("/{room_id}")
    public ResponseEntity<rooms> updateRoom(@PathVariable("room_id") Integer id, @RequestBody rooms roomDetails) {
        if (!roomsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        roomDetails.setRoomId(id);
        rooms updatedRoom = roomsRepository.save(roomDetails);
        return ResponseEntity.ok(updatedRoom);
    }

    // Delete a room by ID
    @DeleteMapping("/{room_id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("room_id") Integer id) {
        if (!roomsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        roomsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
