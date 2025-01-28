package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;

public class PersonalInfoController extends HelloController {
    // UI Fields
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private Button roomsButton;
    @FXML private Button signOutButton;
    @FXML private Button homeButton;

    @FXML
    public void initialize() {
        // Set fields to read-only
        firstNameField.setEditable(false);
        lastNameField.setEditable(false);
        emailField.setEditable(false);
        if (phoneField != null) {
            phoneField.setEditable(false);
        }

        // Setup button click handlers using parent class navigation methods
        if (homeButton != null) {
            homeButton.setOnAction(event -> navigateToHome());
        }
        if (signOutButton != null) {
            signOutButton.setOnAction(event -> handleSignOut());
        }
        if (roomsButton != null) {
            roomsButton.setOnAction(event -> home_to_rooms());
        }
    }

    @Override
    public void initData(User user) {
        this.currentUser = user;
        if (user != null) {
            displayUserData(user);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No user data available");
            alert.showAndWait();
        }
    }

    private void displayUserData(User user) {
        if (firstNameField != null) {
            firstNameField.setText(user.getFirstName());
        }
        if (lastNameField != null) {
            lastNameField.setText(user.getLastName());
        }
        if (emailField != null) {
            emailField.setText(user.getEmail());
        }
        // Handle phone field if it exists and user has phone data
        if (phoneField != null) {
            phoneField.setText(""); // Set default or get from user if available
        }
    }
}