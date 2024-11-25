package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.model.users;
import com.ensam.hotelalrbadr.api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class usersController {

    @Autowired
    private UsersRepository usersRepository;

    // Get all users
    @GetMapping
    public List<users> getAllUsers() {
        return usersRepository.findAll();
    }

    // Get user by user_id
    @GetMapping("/{user_id}")
    public ResponseEntity<users> getUserById(@PathVariable("user_id") Integer user_id) {
        Optional<users> user = usersRepository.findById(user_id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<users> createUser(@RequestBody users user) {
        users savedUser = usersRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Update an existing user
    @PutMapping("/{user_id}")
    public ResponseEntity<users> updateUser(@PathVariable("user_id") Integer user_id, @RequestBody users user) {
        if (!usersRepository.existsById(user_id)) {
            return ResponseEntity.notFound().build();
        }
        user.setUserId(user_id);
        users updatedUser = usersRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete a user by user_id
    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("user_id") Integer user_id) {
        Optional<users> user = usersRepository.findById(user_id);
        if (user.isPresent()) {
            usersRepository.delete(user.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
