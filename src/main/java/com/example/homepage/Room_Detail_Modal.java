package com.example.homepage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Room_Detail_Modal {

    @FXML
    private Button bookNowButton;

    public void detail_to_payment() throws IOException {
        Stage stage3 = (Stage) bookNowButton.getScene().getWindow();
        stage3.close();
        Stage stage2 = new Stage();
        stage2.setTitle("payment page");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Payment_Page.fxml"));
        stage2.setScene(new Scene(fxmlLoader.load(), 1200, 700));
        stage2.show();
    }

}
