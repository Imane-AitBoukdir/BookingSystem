package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.model.Room;
import com.ensam.hotelalrbadr.api.model.Services;
import com.ensam.hotelalrbadr.api.model.User;
import com.ensam.hotelalrbadr.api.repository.RoomServicesRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RoomDetailModalController {
    @FXML public ImageView roomImage;
    @FXML public Label roomTitle;
    @FXML public TextArea roomDescription;
    @FXML private Label priceLabel;
    @FXML private Button bookNowButton;
    @FXML private Button closeButton;
    @FXML private FlowPane servicesContainer;

    private Room currentRoom;
    private final RoomServicesRepository roomServicesRepository;

    public RoomDetailModalController() {
        this.roomServicesRepository = new RoomServicesRepository();
    }

    @FXML
    private void initialize() {
        if (closeButton != null) {
            closeButton.setOnAction(event -> {
                Stage stage = (Stage) closeButton.getScene().getWindow();
                stage.close();
            });
        }

        if (bookNowButton != null) {
            bookNowButton.setOnAction(event -> handleBookNow());
        }

        if (servicesContainer != null) {
            servicesContainer.setHgap(20);
            servicesContainer.setVgap(20);
        }
    }

    public void setRoom(Room room) {
        this.currentRoom = room;
        displayRoomDetails();
        displayRoomServices();
    }

    private void displayRoomDetails() {
        if (currentRoom == null) return;

        try {
            System.out.println("Detail View Image Path: " + currentRoom.getDetailViewImage());
            String imagePath = currentRoom.getDetailViewImage();
            if (!imagePath.startsWith("/")) {
                imagePath = "/" + imagePath;
            }

            var resourceUrl = getClass().getResource(imagePath);
            if (resourceUrl == null) {
                throw new RuntimeException("Detail view image resource not found: " + imagePath);
            }

            Image image = new Image(resourceUrl.toExternalForm());
            roomImage.setImage(image);

        } catch (Exception e) {
            System.out.println("Couldn't load detail view image for room: " + currentRoom.getName());
            e.printStackTrace();
            try {
                Image defaultImage = new Image(
                        getClass().getResource("/homePageImages/details/default-detail-room.png").toExternalForm()
                );
                roomImage.setImage(defaultImage);
            } catch (Exception ex) {
                System.out.println("Failed to load default detail view image");
                ex.printStackTrace();
            }
        }

        roomTitle.setText(currentRoom.getName());
        roomDescription.setText(currentRoom.getDescription());
        priceLabel.setText(String.format("%.2f DH", currentRoom.getPrice()));
    }

    private void displayRoomServices() {
        if (currentRoom == null || servicesContainer == null) return;

        try {
            servicesContainer.getChildren().clear();
            List<Services> roomServices = roomServicesRepository.getServicesForRoom(currentRoom.getId());

            for (Services service : roomServices) {
                ImageView serviceIcon = createServiceIcon(service);
                servicesContainer.getChildren().add(serviceIcon);
            }
        } catch (Exception e) {
            System.out.println("Error showing room services: " + e.getMessage());
        }
    }

    private ImageView createServiceIcon(Services service) {
        ImageView iconView = new ImageView();
        try {
            String iconPath = service.getFormattedIconUrl();
            Image icon = new Image(getClass().getResource(iconPath).toExternalForm());
            iconView.setImage(icon);
            iconView.setFitWidth(30);
            iconView.setFitHeight(30);
        } catch (Exception e) {
            System.out.println("Couldn't load icon for service: " + service.getName());
        }
        return iconView;
    }

    public static void setLoggedInUser(User user) {
    }


    @FXML
    private void handleBookNow() {
        try {
            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Booking Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Congratulations! You successfully booked your Suite");
            alert.showAndWait();

            // Close the current modal window
            Stage currentStage = (Stage) bookNowButton.getScene().getWindow();
            currentStage.close();

            // Load My_Reservations.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/homepage/My_Reservations.fxml"));
            Parent root = loader.load();

            // Get the primary stage and set the new scene
            Stage primaryStage = (Stage) currentStage.getOwner();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Error navigating to reservations page: " + e.getMessage());
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setHeaderText("Could not navigate to reservations page");
            alert.setContentText("An error occurred while trying to open the reservations page.");
            alert.showAndWait();
        }
    }
}