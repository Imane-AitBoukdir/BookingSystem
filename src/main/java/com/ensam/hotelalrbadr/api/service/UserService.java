// src/main/java/com/ensam/hotelalrbadr/api/service/UserService.java
package com.ensam.hotelalrbadr.api.service;

import com.ensam.hotelalrbadr.api.model.User;
import com.ensam.hotelalrbadr.api.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public void registerUser(String firstName, String lastName, String email, String password) {
        // Check if user already exists
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already registered");
        }

        // Create new user
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password); // In a real app, you should hash this password

        // Save user
        userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }
}