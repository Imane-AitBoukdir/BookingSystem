package com.ensam.hotelalrbadr.api.repository;

import com.ensam.hotelalrbadr.api.config.DatabaseConfig;
import com.ensam.hotelalrbadr.api.model.paymentinfo;
import java.sql.*;
import java.time.LocalDate;

// Repository class for handling all database operations related to payment information
public class PaymentInfoRepository {
    private final DatabaseConfig dbConfig;

    public PaymentInfoRepository() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    // Saves a new payment record to the database
    public void save(paymentinfo payment) {
        String sql = "INSERT INTO payment_info (user_id, credit_card_number, expiration_date, " +
                "security_code, billing_address) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the prepared statement
            pstmt.setLong(1, payment.getUser_id());
            pstmt.setString(2, payment.getCredit_card_number());
            pstmt.setDate(3, Date.valueOf(payment.getExpiration_date()));
            pstmt.setString(4, payment.getSecurity_code());
            pstmt.setString(5, payment.getBilling_address());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating payment record failed, no rows affected.");
            }

            // Retrieve and set the auto-generated payment_id
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payment.setPayment_id(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating payment failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving payment information", e);
        }
    }

    // Retrieves payment information for a specific user
    public paymentinfo findByUserId(Long userId) {
        String sql = "SELECT * FROM payment_info WHERE user_id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaymentInfo(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding payment info for user: " + userId, e);
        }
        return null;
    }

    // Finds a specific payment by its ID
    public paymentinfo findById(Long paymentId) {
        String sql = "SELECT * FROM payment_info WHERE payment_id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, paymentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaymentInfo(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding payment with ID: " + paymentId, e);
        }
        return null;
    }

    // Updates an existing payment record
    public void update(paymentinfo payment) {
        String sql = "UPDATE payment_info SET credit_card_number = ?, expiration_date = ?, " +
                "security_code = ?, billing_address = ? WHERE payment_id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, payment.getCredit_card_number());
            pstmt.setDate(2, Date.valueOf(payment.getExpiration_date()));
            pstmt.setString(3, payment.getSecurity_code());
            pstmt.setString(4, payment.getBilling_address());
            pstmt.setLong(5, payment.getPayment_id());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating payment failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating payment information", e);
        }
    }

    // Helper method to convert database results to a paymentinfo object
    private paymentinfo mapResultSetToPaymentInfo(ResultSet rs) throws SQLException {
        paymentinfo payment = new paymentinfo();
        payment.setPayment_id(rs.getLong("payment_id"));
        payment.setUser_id(rs.getLong("user_id"));
        payment.setCredit_card_number(rs.getString("credit_card_number"));
        payment.setExpiration_date(rs.getDate("expiration_date").toLocalDate());
        payment.setSecurity_code(rs.getString("security_code"));
        payment.setBilling_address(rs.getString("billing_address"));
        return payment;
    }
}