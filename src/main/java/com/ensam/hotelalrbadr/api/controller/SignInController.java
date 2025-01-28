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

public class SignInController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button signInButton;
    @FXML private Hyperlink signUpLink;

    private final UserRepository userRepository;

    public SignInController() {
        this.userRepository = new UserRepository();
    }

    @FXML
    private void handleSignIn() {
        try {
            String email = emailField.getText().trim();
            String password = passwordField.getText();

            if (!validateInput(email, password)) {
                return;
            }

            String hashedPassword = hashPassword(password);
            User user = userRepository.findByEmail(email);

            if (user != null && user.getPassword().equals(hashedPassword)) {
                showSuccess("Login Successful", "Welcome back, " + user.getFirstName() + "!");
                navigateToHome(user);
            } else {
                showError("Login Failed", "Invalid email or password");
            }
        } catch (NoSuchAlgorithmException e) {
            showError("System Error", "Could not process login. Please try again.");
            e.printStackTrace();
        } catch (Exception e) {
            showError("Login Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            showError("Validation Error", "Please fill in all fields");
            return false;
        }

        if (!email.contains("@")) {
            showError("Validation Error", "Please enter a valid email address");
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
    private void switchToSignUp() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/homepage/SIGN_UP.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showError("Navigation Error", "Could not load sign up page: " + e.getMessage());
        }
    }

    private void navigateToHome(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/homepage/HomePageLoggedin.fxml"));
            Parent root = loader.load();

            // Get the controller and set the user data
            HelloController controller = loader.getController();
            controller.initData(user);  // Pass the logged in user

            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showError("Navigation Error", "Could not navigate to home page: " + e.getMessage());
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