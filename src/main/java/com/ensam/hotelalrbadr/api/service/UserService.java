package com.ensam.hotelalrbadr.api.service;

import com.ensam.hotelalrbadr.api.model.User;
import com.ensam.hotelalrbadr.api.repository.UserRepository;

/**
 * Service class that handles all user-related operations.
 * This class sits between the controllers and the repository,
 * adding business logic and validation.
 */
public class UserService {
    // Reference to the user repository for database operations
    private final UserRepository userRepository;

    /**
     * Creates a new UserService
     */
    public UserService() {
        this.userRepository = new UserRepository();
    }

    /**
     * Registers a new user in the system
     *
     * @param firstName User's first name
     * @param lastName User's last name
     * @param email User's email address
     * @param password User's password
     * @throws RuntimeException if registration fails
     */
    public void registerUser(String firstName, String lastName, String email, String password) {
        // First, validate all input
        validateRegistrationInput(firstName, lastName, email, password);

        // Check if email is already registered
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("This email is already registered");
        }

        // Create new user
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);  // Note: In a real app, password should be hashed

        // Save user to database
        userRepository.save(user);
    }

    /**
     * Logs in a user with email and password
     *
     * @param email User's email address
     * @param password User's password
     * @return User object if login successful
     * @throws RuntimeException if login fails
     */
    public User login(String email, String password) {
        // Validate login input
        validateLoginInput(email, password);

        // Try to find user by email
        User user = userRepository.findByEmail(email);

        // Check if user exists and password matches
        if (user == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }

    /**
     * Finds a user by their ID
     *
     * @param userId ID of the user to find
     * @return User object if found
     * @throws RuntimeException if user not found
     */
    public User findById(Long userId) {
        // Validate user ID
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        // Try to find user
        User user = userRepository.findById(userId);

        // Check if user was found
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user;
    }

    /**
     * Validates user input for registration
     */
    private void validateRegistrationInput(String firstName, String lastName, String email, String password) {
        // Check first name
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }

        // Check last name
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }

        // Check email format
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Check password length
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
    }

    /**
     * Validates user input for login
     */
    private void validateLoginInput(String email, String password) {
        // Check email
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        // Check password
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
    }
}