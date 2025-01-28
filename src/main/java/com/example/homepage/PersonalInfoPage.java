package com.example.homepage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PersonalInfoPage {

    @FXML
    private Button aboutUsButton;

    @FXML
    private Button contactUsButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private Button homeButton;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button roomsButton;

    @FXML
    private Button servicesButton;

    @FXML
    private Button signOutButton;

    @FXML
    public void handleSignOut() throws IOException {
        // Clear any user session data if you're maintaining it
        // UserSession.getInstance().clearSession(); // Uncomment if you have session management

        // Close the current window
        Stage currentStage = (Stage) signOutButton.getScene().getWindow();
        currentStage.close();

        // Open the initial home page
        Stage newStage = new Stage();
        newStage.setTitle("Home Page");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        newStage.setScene(new Scene(fxmlLoader.load(), 1200, 700));
        newStage.show();
    }

}
