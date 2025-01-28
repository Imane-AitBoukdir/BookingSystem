package com.example.administrator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardController {

    @FXML
    private Label availableRoomsLabel;

    @FXML
    private Button bookingsButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Label percentageLabel;

    @FXML
    private Button roomsButton;



    public void dashBoard_To_Rooms() throws IOException {
        Stage stage = (Stage) roomsButton.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        stage1.setTitle("Room Management ");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RoomManagementAdmin.fxml"));
        stage1.setScene(new Scene(fxmlLoader.load(), 1200, 700));
        stage1.show();
    }



}
