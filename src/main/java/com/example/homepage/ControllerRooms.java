package com.example.homepage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerRooms {

    @FXML
    private Label EconomyTitle;

    @FXML
    private Label FamilyTitle;

    @FXML
    private Label LuxuryTitle;

    @FXML
    private Label RoomsTitle;

    public void pop_Up_view() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Room_Detail_Modal.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 940, 633));
        stage.setTitle("Room_Detail_Modal");
        stage.show();

    }

}
