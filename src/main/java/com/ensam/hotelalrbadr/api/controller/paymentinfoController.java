package com.ensam.hotelalrbadr.api.controller;

import com.ensam.hotelalrbadr.api.model.paymentinfo;
import com.ensam.hotelalrbadr.api.repository.PaymentInfoRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.time.LocalDate;

public class paymentinfoController {
    // FXML injected fields from Payment_Page.fxml
    @FXML private TextField cardNameField;
    @FXML private TextField cardNumberField;
    @FXML private TextField expirationDateField;
    @FXML private TextField cvvField;
    @FXML private TextField phoneNumberField;
    @FXML private Button checkOutButton;

    // Labels for displaying booking details
    @FXML private Label totalAmountLabel;
    @FXML private Label bookingDatesLabel;
    @FXML private Label guestsLabel;

    // Repository instance for database operations
    private final PaymentInfoRepository paymentRepository;

    // Pattern constants for validation
    private static final String CARD_NUMBER_PATTERN = "\\d{16}";
    private static final String CVV_PATTERN = "\\d{3,4}";
    private static final String PHONE_PATTERN = "\\d{10}";

    public paymentinfoController() {
        this.paymentRepository = new PaymentInfoRepository();
    }

    @FXML
    public void initialize() {
        // Set up real-time input validation and formatting
        setupInputFormatters();
    }

    private void setupInputFormatters() {
        // Card number validation (16 digits only)
        cardNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cardNumberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 16) {
                cardNumberField.setText(oldValue);
            }
        });

        // CVV validation (3-4 digits only)
        cvvField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cvvField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 4) {
                cvvField.setText(oldValue);
            }
        });

        // Expiration date formatter (MM/YY format)
        expirationDateField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > oldValue.length()) {
                if (newValue.length() == 2 && !newValue.contains("/")) {
                    expirationDateField.setText(newValue + "/");
                } else if (newValue.length() > 5) {
                    expirationDateField.setText(oldValue);
                }
            }
        });

        // Phone number validation (10 digits only)
        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                phoneNumberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 10) {
                phoneNumberField.setText(oldValue);
            }
        });
    }

    @FXML
    private void payment_to_myReservation(ActionEvent event) {
        if (validatePaymentForm()) {
            try {
                // Create and save payment information
                paymentinfo payment = createPaymentInfo();
                paymentRepository.save(payment);

                // Show success message and navigate to reservations
                showSuccessAlert();
                navigateToReservations(event);
            } catch (Exception e) {
                showErrorAlert("Payment processing failed: " + e.getMessage());
            }
        }
    }

    private paymentinfo createPaymentInfo() {
        paymentinfo payment = new paymentinfo();
        payment.setUser_id(1L); // This should come from your user session
        payment.setCredit_card_number(cardNumberField.getText().trim());
        payment.setExpiration_date(parseExpirationDate(expirationDateField.getText().trim()));
        payment.setSecurity_code(cvvField.getText().trim());
        payment.setBilling_address(""); // Add billing address field if needed
        return payment;
    }

    private boolean validatePaymentForm() {
        StringBuilder errors = new StringBuilder();

        if (cardNameField.getText().trim().isEmpty()) {
            errors.append("Card holder name is required.\n");
        }
        if (!cardNumberField.getText().matches(CARD_NUMBER_PATTERN)) {
            errors.append("Card number must be 16 digits.\n");
        }
        if (!isValidExpirationDate(expirationDateField.getText())) {
            errors.append("Invalid expiration date (use MM/YY format).\n");
        }
        if (!cvvField.getText().matches(CVV_PATTERN)) {
            errors.append("CVV must be 3 or 4 digits.\n");
        }
        if (!phoneNumberField.getText().matches(PHONE_PATTERN)) {
            errors.append("Phone number must be 10 digits.\n");
        }

        if (errors.length() > 0) {
            showErrorAlert(errors.toString());
            return false;
        }
        return true;
    }

    private LocalDate parseExpirationDate(String date) {
        String[] parts = date.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = 2000 + Integer.parseInt(parts[1]);
        return LocalDate.of(year, month, 1);
    }

    private boolean isValidExpirationDate(String date) {
        if (!date.matches("\\d{2}/\\d{2}")) {
            return false;
        }
        try {
            LocalDate expirationDate = parseExpirationDate(date);
            return expirationDate.isAfter(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }

    private void navigateToReservations(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/homepage/MyReservations.fxml")
            );
            Parent root = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            showErrorAlert("Error navigating to reservations: " + e.getMessage());
        }
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Payment processed successfully!");
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}