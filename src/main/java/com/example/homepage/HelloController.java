package com.example.homepage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private Button aboutUsButton;

    @FXML
    private DatePicker arriveeField;

    @FXML
    private Button contactUsButton;

    @FXML
    private DatePicker departField;

    @FXML
    private TextField destinationField;

    @FXML
    private Button homeButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button roomsButton;

    @FXML
    private Button searchBody;

    @FXML
    private Button servicesButton;

    @FXML
    private Label welcomeLabel;
    @FXML
    private Button closeButton;
    @FXML
    private Button go_Back_button;
    @FXML
    private Node quit_Button;

// to close a window and go to another

    public void home_to_rooms() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        stage1.setTitle("rooms");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RoomsPage.fxml"));
        stage1.setScene(new Scene(fxmlLoader.load(), 1200, 700));
        stage1.show();
    }

    public void home_to_signup() throws IOException {
        Stage stage = (Stage) roomsButton.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        stage1.setTitle("rooms");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SIGN_UP.fxml"));
        stage1.setScene(new Scene(fxmlLoader.load(), 1200, 700));
        stage1.show();
    }
// the quit button that closes a window so i need to add a close button that have the id closeButton in the program
    public void close_fn(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
// to show two stages without closing the previous (good for the pop up)

    public void pop_Up_view() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Room_Detail_Modal.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 1200, 700));
        stage.setTitle("Room_Detail_Modal");
        stage.show();

    }

    public void go_back() throws IOException {
        Stage stage = (Stage) go_Back_button.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        stage1.setScene(new Scene(fxmlLoader.load(), 1200, 700));
        stage1.setTitle("HomePage");
        stage1.show();
    }

    public void homePageQuit(){
        Stage stage = (Stage) quit_Button.getScene().getWindow();
        stage.close();
    }
}
