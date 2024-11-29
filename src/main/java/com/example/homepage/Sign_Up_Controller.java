package com.example.homepage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Sign_Up_Controller {

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Hyperlink signInLink;

    @FXML
    private Button signUpButton;

    public void signup_to_signup() throws IOException {
        Stage stage = (Stage) signInLink.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        stage1.setTitle("SIGN_IN");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SIGN_IN.fxml"));
        stage1.setScene(new Scene(fxmlLoader.load(), 1200, 700));
        stage1.show();
    }

}
