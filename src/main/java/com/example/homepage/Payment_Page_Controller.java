package com.example.homepage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Payment_Page_Controller {

    @FXML
    private Button checkOutButton;

    public void payment_to_myReservation() throws IOException {
        Stage stage = (Stage) checkOutButton.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        stage1.setTitle("My_Reservation");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("My_Reservations.fxml"));
        stage1.setScene(new Scene(fxmlLoader.load(), 1200, 700));
        stage1.show();
    }

}
