package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.model.User;
import com.ensam.hotelalrbadr.api.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpController {
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button signUpButton;
    @FXML private Hyperlink signInLink;

    private final UserRepository userRepository;

    public SignUpController() {
        this.userRepository = new UserRepository();
    }

    @FXML
    private void handleSignUp() {
        try {
            // Get and trim input values
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText();

            if (!validateInput(firstName, lastName, email, password)) {
                return;
            }

            // Check if email already exists
            if (userRepository.findByEmail(email) != null) {
                showError("Registration Error", "This email is already registered");
                return;
            }

            // Create new user with hashed password
            User newUser = new User();
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setEmail(email);
            newUser.setPassword(hashPassword(password));

            // Save user to database
            userRepository.save(newUser);

            showSuccess("Registration Successful",
                    "Welcome " + firstName + "! You can now sign in with your email and password.");
            switchToSignIn();

        } catch (NoSuchAlgorithmException e) {
            showError("System Error", "Could not process registration. Please try again.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            showError("Validation Error", e.getMessage());
        } catch (Exception e) {
            showError("Registration Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validateInput(String firstName, String lastName, String email, String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showError("Validation Error", "Please fill in all fields");
            return false;
        }

        if (!email.contains("@")) {
            showError("Validation Error", "Please enter a valid email address");
            return false;
        }

        if (password.length() < 6) {
            showError("Validation Error", "Password must be at least 6 characters long");
            return false;
        }

        if (!firstName.matches("[a-zA-Z\\s]+") || !lastName.matches("[a-zA-Z\\s]+")) {
            showError("Validation Error", "Names can only contain letters and spaces");
            return false;
        }

        return true;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @FXML
    private void switchToSignIn() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/homepage/SIGN_IN.fxml"));
            Stage stage = (Stage) firstNameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showError("Navigation Error", "Could not load sign-in page: " + e.getMessage());
        }
    }

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSuccess(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}