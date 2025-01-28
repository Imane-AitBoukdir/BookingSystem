package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.model.Room;
import com.ensam.hotelalrbadr.api.repository.RoomRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class RoomsController extends HelloController {
    private static final Logger LOGGER = Logger.getLogger(RoomsController.class.getName());

    @FXML private VBox luxuryRoomsContainer;
    @FXML private VBox familyRoomsContainer;
    @FXML private VBox economyRoomsContainer;

    private final RoomRepository roomRepository;

    public RoomsController() {
        this.roomRepository = new RoomRepository();
    }

    @FXML
    public void initialize() {
        try {
            LOGGER.info("Initializing RoomsController");
            validateContainers();
            verifyImageResources();
            loadAllRoomCategories();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during initialization", e);
            showError("Failed to initialize rooms view");
        }
    }

    private void verifyImageResources() {
        LOGGER.info("Verifying image resources");
        String[] categoriesToCheck = {"eco", "lux", "fam"};
        for (String category : categoriesToCheck) {
            for (int i = 1; i <= 6; i++) {
                verifyImageResource(category, i);
            }
        }
    }

    private void verifyImageResource(String category, int index) {
        String listPath = "/homePageImages/list/" + category + "list" + index + ".png";
        String detailPath = "/homePageImages/details/" + category + "detail" + index + ".png";

        var listResource = getClass().getResource(listPath);
        var detailResource = getClass().getResource(detailPath);

        if (listResource == null) {
            LOGGER.warning("Missing list image resource: " + listPath);
        }
        if (detailResource == null) {
            LOGGER.warning("Missing detail image resource: " + detailPath);
        }
    }

    private void validateContainers() {
        LOGGER.info("Validating FXML containers");
        if (luxuryRoomsContainer == null || familyRoomsContainer == null || economyRoomsContainer == null) {
            LOGGER.severe("One or more FXML containers not initialized");
            throw new RuntimeException("FXML containers not properly initialized");
        }
    }

    private void loadAllRoomCategories() {
        LOGGER.info("Loading all room categories");
        loadRoomCategory("luxury", luxuryRoomsContainer);
        loadRoomCategory("family", familyRoomsContainer);
        loadRoomCategory("economy", economyRoomsContainer);
    }

    private void loadRoomCategory(String category, VBox container) {
        try {
            LOGGER.info("Loading rooms for category: " + category);
            container.getChildren().clear();

            List<Room> rooms = roomRepository.findByCategory(category);
            LOGGER.info("Found " + rooms.size() + " rooms in category: " + category);

            if (rooms.isEmpty()) {
                showEmptyMessage(container, category);
                return;
            }

            HBox roomRow = createRoomRow(rooms);
            container.getChildren().add(roomRow);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading " + category + " rooms", e);
            showError("Failed to load " + category + " rooms");
        }
    }

    private HBox createRoomRow(List<Room> rooms) {
        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER);

        for (Room room : rooms) {
            try {
                VBox roomCard = createRoomCard(room);
                row.getChildren().add(roomCard);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error creating card for room: " + room.getName(), e);
            }
        }

        return row;
    }

    private VBox createRoomCard(Room room) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 5;");

        ImageView roomImage = createRoomImage(room);
        Label nameLabel = createStyledLabel(room.getName(), "-fx-font-weight: bold; -fx-font-size: 16;");
        Label priceLabel = createStyledLabel(
                String.format("%.2f DH", room.getPrice()),
                "-fx-text-fill: #004d7c; -fx-font-weight: bold;"
        );
        Button viewButton = createViewButton(room);

        card.getChildren().addAll(roomImage, nameLabel, priceLabel, viewButton);
        return card;
    }

    private ImageView createRoomImage(Room room) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(291);
        imageView.setFitHeight(166);

        try {
            String imagePath = room.getListViewImage();
            if (!imagePath.startsWith("/")) {
                imagePath = "/" + imagePath;
            }

            LOGGER.info("Loading room image from path: " + imagePath);
            var resourceUrl = getClass().getResource(imagePath);

            if (resourceUrl == null) {
                LOGGER.warning("Image resource not found: " + imagePath);
                loadDefaultImage(imageView);
                return imageView;
            }

            Image image = new Image(resourceUrl.toExternalForm(), 291, 166, true, true);
            imageView.setImage(image);

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to load image for room: " + room.getName(), e);
            loadDefaultImage(imageView);
        }

        return imageView;
    }

    private void loadDefaultImage(ImageView imageView) {
        try {
            Image defaultImage = new Image(
                    getClass().getResource("/homePageImages/list/default-room.png").toExternalForm(),
                    291, 166, true, true
            );
            imageView.setImage(defaultImage);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to load default image", ex);
        }
    }

    private Label createStyledLabel(String text, String style) {
        Label label = new Label(text);
        label.setStyle(style);
        return label;
    }

    private Button createViewButton(Room room) {
        Button button = new Button("View The Room");
        button.setStyle(
                "-fx-background-color: #008CE3; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-pref-width: 200;"
        );
        button.setOnAction(e -> openRoomDetails(room));
        return button;
    }

    private void openRoomDetails(Room room) {
        try {
            LOGGER.info("Opening detail modal for room: " + room.getName());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/homepage/Room_Detail_Modal.fxml"));
            Parent modalWindow = loader.load();

            RoomDetailModalController modalController = loader.getController();
            modalController.setRoom(room);

            Stage stage = new Stage();
            stage.setTitle("Room Details");
            stage.setScene(new Scene(modalWindow));
            stage.show();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Room Detail Modal", e);
            showError("Failed to open room details");
        }
    }

    private void showEmptyMessage(VBox container, String category) {
        Label messageLabel = createStyledLabel(
                "No " + category + " rooms available",
                "-fx-text-fill: #666666; -fx-font-size: 14;"
        );
        container.getChildren().add(messageLabel);
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}