package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.model.User;
import com.ensam.hotelalrbadr.api.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpController {
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private final UserRepository userRepository;

    public SignUpController() {
        this.userRepository = new UserRepository();
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        try {
            // Validate input
            if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
                    emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields are required!");
                return;
            }

            // Check if email already exists
            if (userRepository.findByEmail(emailField.getText().trim()) != null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Email already registered!");
                return;
            }

            // Create and save user
            User user = new User();
            user.setFirstName(firstNameField.getText().trim());
            user.setLastName(lastNameField.getText().trim());
            user.setEmail(emailField.getText().trim());
            user.setPassword(hashPassword(passwordField.getText()));

            userRepository.save(user);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Registration successful!");
            switchToSignIn(event);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Registration failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void switchToSignIn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/homepage/SIGN_IN.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error loading sign-in page: " + e.getMessage());
        }
    }
}