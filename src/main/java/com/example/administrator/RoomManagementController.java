package com.example.administrator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class RoomManagementController {

    @FXML
    private Button addButton;

    @FXML
    private Label addRoomLabel;

    @FXML
    private Button bookingsButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Label deleteRoomLabel;

    @FXML
    private Label imageLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label roomManagementLabel;

    @FXML
    private Button roomsButton;

    @FXML
    private Label services;

    @FXML
    public void rooms_To_Dashboard() throws IOException {
        Stage stage = (Stage) dashboardButton.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        stage1.setTitle("DashBoard");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DashBoardAdmin.fxml"));
        stage1.setScene(new Scene(fxmlLoader.load(), 1200, 700));
        stage1.show();
    }
}
