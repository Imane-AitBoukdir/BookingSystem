package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.model.Reservation;
import com.ensam.hotelalrbadr.api.model.User;
import com.ensam.hotelalrbadr.api.service.ReservationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyReservationsController extends HelloController {
    @FXML private VBox reservationsContainer;

    private final DateTimeFormatter dateFormatter;
    private final ReservationService reservationService;

    public MyReservationsController() {
        this.dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        this.reservationService = new ReservationService();
    }

    @FXML
    public void initialize() {
        if (reservationsContainer != null) {
            reservationsContainer.setSpacing(10);
        }
    }

    @Override
    public void initData(User user) {
        super.initData(user);
        if (user != null) {
            loadAndShowReservations();
        }
    }

    private void loadAndShowReservations() {
        if (currentUser == null || reservationsContainer == null) {
            return;
        }

        try {
            reservationsContainer.getChildren().clear();
            List<Reservation> userReservations =
                    reservationService.getReservationsForUser(currentUser.getId());

            for (Reservation reservation : userReservations) {
                HBox reservationCard = createReservationCard(reservation);
                if (reservationCard != null) {
                    reservationsContainer.getChildren().add(reservationCard);
                }
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load your reservations. Please try again.");
        }
    }

    private HBox createReservationCard(Reservation reservation) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/homepage/reservation_item.fxml"));
            HBox card = loader.load();

            setReservationCardComponents(card, reservation);
            return card;
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not create reservation display");
            return null;
        }
    }

    private void setReservationCardComponents(HBox card, Reservation reservation) {
        ImageView roomImage = (ImageView) card.lookup("#roomImage");
        Label roomNameLabel = (Label) card.lookup("#roomName");
        Label roomDescLabel = (Label) card.lookup("#roomDescription");
        Label roomTypeLabel = (Label) card.lookup("#roomType");
        Label checkInLabel = (Label) card.lookup("#checkInDate");
        Label checkOutLabel = (Label) card.lookup("#checkOutDate");
        Label priceLabel = (Label) card.lookup("#totalPrice");
        Button editButton = (Button) card.lookup("#editButton");

        if (roomImage != null && reservation.getRoom_image() != null) {
            loadRoomImage(roomImage, reservation.getRoom_image());
        }

        if (roomNameLabel != null) roomNameLabel.setText(reservation.getRoom_name());
        if (roomDescLabel != null) roomDescLabel.setText(reservation.getDescription());
        if (roomTypeLabel != null) roomTypeLabel.setText("Standard");

        setDateLabels(checkInLabel, checkOutLabel, reservation);
        setPriceAndEditButton(priceLabel, editButton, reservation);
    }

    private void loadRoomImage(ImageView imageView, String imagePath) {
        try {
            Image image = new Image(getClass().getResource(imagePath).toExternalForm());
            imageView.setImage(image);
        } catch (Exception e) {
            try {
                Image defaultImage = new Image(
                        getClass().getResource("/homePageImages/reservation/default.png").toExternalForm());
                imageView.setImage(defaultImage);
            } catch (Exception ex) {
                // If default image also fails, leave the image view empty
            }
        }
    }

    private void setDateLabels(Label checkInLabel, Label checkOutLabel, Reservation reservation) {
        if (checkInLabel != null && reservation.getCheck_in_date() != null) {
            String checkInText = reservation.getCheck_in_date().toLocalDate()
                    .format(dateFormatter);
            checkInLabel.setText(checkInText);
        }

        if (checkOutLabel != null && reservation.getCheck_out_date() != null) {
            String checkOutText = reservation.getCheck_out_date().toLocalDate()
                    .format(dateFormatter);
            checkOutLabel.setText(checkOutText);
        }
    }

    private void setPriceAndEditButton(Label priceLabel, Button editButton, Reservation reservation) {
        if (priceLabel != null) {
            priceLabel.setText(String.format("%.2f DHS", reservation.getTotal_price()));
        }

        if (editButton != null) {
            if (reservation.isUpcoming()) {
                editButton.setOnAction(event -> showEditDialog(reservation));
            } else {
                editButton.setDisable(true);
                editButton.setText("Past Reservation");
            }
        }
    }

    private void showEditDialog(Reservation reservation) {
        Dialog<ButtonType> dialog = createEditDialog(reservation);
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                updateReservation(reservation, dialog);
            }
        });
    }

    private Dialog<ButtonType> createEditDialog(Reservation reservation) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Reservation");
        dialog.setHeaderText("Update Reservation Details");

        // Add the standard buttons (OK and Cancel)
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create the content
        VBox content = new VBox(10);
        DatePicker checkInPicker = new DatePicker(reservation.getCheck_in_date().toLocalDate());
        DatePicker checkOutPicker = new DatePicker(reservation.getCheck_out_date().toLocalDate());

        content.getChildren().addAll(
                new Label("Check-in Date:"),
                checkInPicker,
                new Label("Check-out Date:"),
                checkOutPicker
        );

        dialog.getDialogPane().setContent(content);
        return dialog;
    }

    private void updateReservation(Reservation reservation, Dialog<ButtonType> dialog) {
        try {
            DatePicker checkInPicker = (DatePicker) ((VBox) dialog.getDialogPane().getContent())
                    .getChildren().get(1);
            DatePicker checkOutPicker = (DatePicker) ((VBox) dialog.getDialogPane().getContent())
                    .getChildren().get(3);

            LocalDate newCheckIn = checkInPicker.getValue();
            LocalDate newCheckOut = checkOutPicker.getValue();

            if (validateDates(newCheckIn, newCheckOut)) {
                reservation.setCheck_in_date(Date.valueOf(newCheckIn));
                reservation.setCheck_out_date(Date.valueOf(newCheckOut));

                reservationService.updateReservation(reservation);
                loadAndShowReservations();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Reservation updated successfully");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not update reservation: " + e.getMessage());
        }
    }

    private boolean validateDates(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select valid dates");
            return false;
        }

        LocalDate today = LocalDate.now();
        if (checkIn.isBefore(today)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Check-in date cannot be in the past");
            return false;
        }

        if (checkOut.isBefore(checkIn)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Check-out date must be after check-in date");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}