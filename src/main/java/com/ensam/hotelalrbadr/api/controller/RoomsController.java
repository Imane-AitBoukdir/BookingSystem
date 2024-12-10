package com.example.homepage;

import com.ensam.hotelalrbadr.api.model.Room;
import com.ensam.hotelalrbadr.api.model.RoomService;
import com.ensam.hotelalrbadr.api.repository.RoomRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class RoomsController {
    @FXML private VBox luxuryRoomsContainer;
    @FXML private VBox familyRoomsContainer;
    @FXML private VBox economyRoomsContainer;

    private final RoomRepository roomRepository = new RoomRepository();

    @FXML
    public void initialize() {
        loadRoomsByCategory("luxury", luxuryRoomsContainer);
        loadRoomsByCategory("family", familyRoomsContainer);
        loadRoomsByCategory("economy", economyRoomsContainer);
    }

    private void loadRoomsByCategory(String category, VBox container) {
        List<Room> rooms = roomRepository.findByCategory(category);
        HBox roomRow = new HBox();
        roomRow.setSpacing(20);

        for (Room room : rooms) {
            VBox roomCard = createRoomCard(room);
            roomRow.getChildren().add(roomCard);
        }

        container.getChildren().add(roomRow);
    }

    private VBox createRoomCard(Room room) {
        VBox card = new VBox();
        card.getStyleClass().add("room-card");
        card.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        // Room image
        ImageView imageView = new ImageView(new Image(room.getImageUrl()));
        imageView.setFitHeight(166);
        imageView.setFitWidth(291);

        // View button
        Button viewButton = new Button("View The Room");
        viewButton.getStyleClass().add("ViewRoom");
        viewButton.setOnAction(e -> showRoomDetails(room));

        card.getChildren().addAll(imageView, viewButton);
        return card;
    }

    private void showRoomDetails(Room room) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/homepage/Rooms_Details_Modal.fxml"));
            Parent root = loader.load();

            RoomDetailModalController controller = loader.getController();
            controller.setRoom(room);

            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}