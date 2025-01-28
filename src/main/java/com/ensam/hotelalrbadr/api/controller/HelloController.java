package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.model.User;
import com.ensam.hotelalrbadr.api.service.BookingManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

public class HelloController {
    // UI Components
    @FXML protected Button loginButton;
    @FXML protected Button signOutButton;
    @FXML protected Button homeButton;
    @FXML protected Button profileButton;
    @FXML protected Label welcomeLabel;
    @FXML protected Button aboutUsButton;
    @FXML protected Button contactUsButton;
    @FXML protected DatePicker checkInDatePicker;
    @FXML protected DatePicker checkOutDatePicker;

    // Current logged-in user
    protected User currentUser;

    // Set user data and update welcome message
    public void initData(User user) {
        this.currentUser = user;
        if (user != null && welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + user.getFirstName());
        }
    }

    // Main navigation methods - named to match FXML
    @FXML
    public void home_to_signup() {
        loadPage("/com/example/homepage/SIGN_IN.fxml");
    }


    @FXML
    public void home_to_rooms() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/homepage/RoomsPage.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the current user
            RoomsController controller = loader.getController();
            if (controller != null) {
                controller.initData(currentUser);
            }

            setScene(root);
        } catch (IOException e) {
            showError("Could not load rooms page");
        }
    }

    public void navigateToAboutUs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/homepage/AboutUs.fxml"));
            Parent root = loader.load();

            HelloController controller = loader.getController();
            controller.initData(currentUser);

            setScene(root);
        } catch (IOException e) {
            showError("Could not load home page");
        }
    }

    public void navigateToRooms() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/homepage/RoomsPage.fxml"));
            Parent root = loader.load();

            HelloController controller = loader.getController();
            controller.initData(currentUser);

            setScene(root);
        } catch (IOException e) {
            showError("Could not load home page");
        }
    }


    public void navigateToContactUs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/homepage/ContactUs.fxml"));
            Parent root = loader.load();

            HelloController controller = loader.getController();
            controller.initData(currentUser);

            setScene(root);
        } catch (IOException e) {
            showError("Could not load home page");
        }
    }

    @FXML
    public void navigateToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/homepage/HomePageLoggedin.fxml"));
            Parent root = loader.load();

            HelloController controller = loader.getController();
            controller.initData(currentUser);

            setScene(root);
        } catch (IOException e) {
            showError("Could not load home page");
        }
    }

    @FXML
    public void navigateToPersonalInfo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/homepage/PersonalInfoPage.fxml"));
            Parent root = loader.load();

            PersonalInfoController controller = loader.getController();
            controller.initData(currentUser);

            setScene(root);
        } catch (IOException e) {
            showError("Could not load personal info page");
        }
    }

    @FXML
    public void handleSignOut() {
        currentUser = null;
        loadPage("/com/example/homepage/HomePage.fxml");
    }

    // Helper methods
    protected void loadPage(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            setScene(root);
        } catch (IOException e) {
            showError("Could not load page");
        }
    }

    protected void setScene(Parent root) {
        Stage stage = null;

        // Try to get stage from available buttons
        if (loginButton != null && loginButton.getScene() != null) {
            stage = (Stage) loginButton.getScene().getWindow();
        } else if (profileButton != null && profileButton.getScene() != null) {
            stage = (Stage) profileButton.getScene().getWindow();
        } else if (homeButton != null && homeButton.getScene() != null) {
            stage = (Stage) homeButton.getScene().getWindow();
        }

        if (stage != null) {
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }



}