package com.example.homepage;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

// Project-specific imports - Note we're now using the Services class instead of RoomService
import com.ensam.hotelalrbadr.api.model.Room;
import com.ensam.hotelalrbadr.api.model.Services;  // Changed from RoomService to Services

import java.io.IOException;

public class RoomDetailModalController {
    @FXML private ImageView roomImage;
    @FXML private Label roomTitle;
    @FXML private TextArea roomDescription;
    @FXML private Label priceLabel;
    @FXML private Button closeButton;
    @FXML private Button bookNowButton;
    @FXML private FlowPane servicesContainer;

    @FXML
    private void initialize() {
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    public void setRoom(Room room) {
        // Set room details
        roomImage.setImage(new Image(room.getImageUrl()));
        roomTitle.setText(room.getName());
        roomDescription.setText(room.getDescription());
        priceLabel.setText(String.format("%.2f DH", room.getPrice()));

        // Load services dynamically
        loadServices(room);
    }

    private void loadServices(Room room) {
        servicesContainer.getChildren().clear();

        // Note that we're now working with Services objects instead of RoomService
        for (Services service : room.getServices()) {
            ImageView serviceIcon = new ImageView(new Image(service.getIconUrl()));
            serviceIcon.setFitHeight(40);
            serviceIcon.setFitWidth(40);

            // Add tooltip with service name
            javafx.scene.control.Tooltip tooltip = new javafx.scene.control.Tooltip(service.getName());
            javafx.scene.control.Tooltip.install(serviceIcon, tooltip);

            servicesContainer.getChildren().add(serviceIcon);
        }
    }

    @FXML
    private void handleBookNow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/homepage/Payment_Page.fxml"));
            Parent root = loader.load();

            // Close the current modal
            Stage currentStage = (Stage) bookNowButton.getScene().getWindow();
            currentStage.close();

            // Show the payment page
            Stage paymentStage = new Stage();
            paymentStage.setScene(new Scene(root));
            paymentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading payment page: " + e.getMessage());
        }
    }
}