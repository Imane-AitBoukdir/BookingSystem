// src/main/java/com/ensam/hotelalrbadr/api/controller/SignInController.java
package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.service.UserService;
import com.ensam.hotelalrbadr.api.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignInController {
    @FXML
    private TextField emailField; // Make sure this matches your fx:id in SIGN_IN.fxml
    @FXML
    private PasswordField passwordField; // Make sure this matches your fx:id in SIGN_IN.fxml

    private final UserService userService;

    public SignInController() {
        this.userService = new UserService();
    }

    @FXML
    private void handleSignIn() {
        try {
            User user = userService.login(
                    emailField.getText(),
                    passwordField.getText()
            );

            // If login successful, load the HomePage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/homepage/HomePage.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void switchToSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/homepage/SIGN_UP.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}