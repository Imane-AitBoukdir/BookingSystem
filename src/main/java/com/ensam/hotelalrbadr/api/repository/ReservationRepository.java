package com.ensam.hotelalrbadr.api.repository;

import com.ensam.hotelalrbadr.api.config.DatabaseConfig;
import com.ensam.hotelalrbadr.api.model.Reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles saving and loading reservations from the database.
 * Think of it as a manager that knows how to talk to the reservations table.
 */
public class ReservationRepository {
    // We need this to connect to the database
    private final DatabaseConfig dbConfig;

    // These are our basic database queries written in SQL

    // Query to save a new reservation
    private static final String INSERT_RESERVATION =
            "INSERT INTO reservations " +
                    "(user_id, room_id, check_in_date, check_out_date, total_price, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    // Query to get all reservations for a user
    private static final String GET_USER_RESERVATIONS =
            "SELECT " +
                    // Get reservation details
                    "r.*, " +
                    // Get room details we need
                    "rm.name as room_name, " +
                    "rm.list_view_image as room_image, " +
                    "rm.description " +
                    "FROM " +
                    // Join reservations with rooms to get room info
                    "reservations r " +
                    "JOIN rooms rm ON r.room_id = rm.id " +
                    "WHERE " +
                    "r.user_id = ? " +
                    "ORDER BY " +
                    "r.check_in_date DESC";  // Show newest first

    // Query to update an existing reservation
    private static final String UPDATE_RESERVATION =
            "UPDATE reservations " +
                    "SET " +
                    "check_in_date = ?, " +
                    "check_out_date = ?, " +
                    "total_price = ?, " +
                    "status = ? " +
                    "WHERE reservation_id = ?";

    /**
     * Creates a new ReservationRepository ready to handle database operations
     */
    public ReservationRepository() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    /**
     * Saves a new reservation to the database
     */
    public void save(Reservation reservation) {
        // Get a database connection and create a prepared statement
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_RESERVATION,
                     Statement.RETURN_GENERATED_KEYS)) {

            // Step 1: Set all the values in our insert statement
            stmt.setInt(1, reservation.getUser_id());
            stmt.setInt(2, reservation.getRoom_id());
            stmt.setDate(3, reservation.getCheck_in_date());
            stmt.setDate(4, reservation.getCheck_out_date());
            stmt.setDouble(5, reservation.getTotal_price());
            stmt.setString(6, reservation.getStatus());

            // Step 2: Execute the insert
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Creating reservation failed, no rows were inserted.");
            }

            // Step 3: Get the ID that the database assigned to our new reservation
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setReservation_id(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating reservation failed, no ID was returned.");
                }
            }
        } catch (SQLException e) {
            // If anything goes wrong, wrap it in a runtime exception
            throw new RuntimeException("Could not save reservation: " + e.getMessage(), e);
        }
    }

    /**
     * Gets all reservations for a specific user
     */
    public List<Reservation> findByUserId(Long userId) {
        // Create a list to hold all the reservations we find
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_USER_RESERVATIONS)) {

            // Set the user ID in our query
            stmt.setLong(1, userId);

            // Execute the query and get the results
            ResultSet rs = stmt.executeQuery();

            // Loop through each row in the result
            while (rs.next()) {
                // Convert the current row to a Reservation object and add it to our list
                Reservation reservation = createReservationFromRow(rs);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not get reservations for user: " + e.getMessage(), e);
        }

        return reservations;
    }

    /**
     * Updates an existing reservation in the database
     */
    public void update(Reservation reservation) {
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_RESERVATION)) {

            // Set all the new values
            stmt.setDate(1, reservation.getCheck_in_date());
            stmt.setDate(2, reservation.getCheck_out_date());
            stmt.setDouble(3, reservation.getTotal_price());
            stmt.setString(4, reservation.getStatus());
            stmt.setInt(5, reservation.getReservation_id());

            // Try to update the reservation
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Could not update reservation - it might not exist anymore.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not update reservation: " + e.getMessage(), e);
        }
    }

    /**
     * Helper method to create a Reservation object from a database row
     */
    private Reservation createReservationFromRow(ResultSet row) throws SQLException {
        // Create a new empty reservation
        Reservation reservation = new Reservation();

        // Fill in the reservation details
        reservation.setReservation_id(row.getInt("reservation_id"));
        reservation.setUser_id(row.getInt("user_id"));
        reservation.setRoom_id(row.getInt("room_id"));
        reservation.setCheck_in_date(row.getDate("check_in_date"));
        reservation.setCheck_out_date(row.getDate("check_out_date"));
        reservation.setTotal_price(row.getDouble("total_price"));
        reservation.setStatus(row.getString("status"));
        reservation.setCreated_at(row.getTimestamp("created_at"));

        // Fill in the room details
        reservation.setRoom_name(row.getString("room_name"));
        reservation.setRoom_image(row.getString("room_image"));
        reservation.setDescription(row.getString("description"));

        return reservation;
    }

    /**
     * Checks if a room is already booked for specific dates
     */
    public boolean isRoomBooked(Integer roomId, Date checkIn, Date checkOut) {
        // This is our query to check for overlapping reservations
        String checkAvailabilityQuery =
                "SELECT COUNT(*) FROM reservations " +
                        "WHERE room_id = ? " +
                        "AND status != 'CANCELLED' " +
                        "AND (" +
                        // Case 1: Check-in date falls during another reservation
                        "(check_in_date BETWEEN ? AND ?) OR " +
                        // Case 2: Check-out date falls during another reservation
                        "(check_out_date BETWEEN ? AND ?) OR " +
                        // Case 3: Reservation spans the entire period
                        "(check_in_date <= ? AND check_out_date >= ?)" +
                        ")";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(checkAvailabilityQuery)) {

            // Set all our parameters
            stmt.setInt(1, roomId);
            stmt.setDate(2, checkIn);
            stmt.setDate(3, checkOut);
            stmt.setDate(4, checkIn);
            stmt.setDate(5, checkOut);
            stmt.setDate(6, checkIn);
            stmt.setDate(7, checkOut);

            // Run the query and get the count of overlapping reservations
            ResultSet rs = stmt.executeQuery();

            // If there are any overlapping reservations, the room is booked
            return rs.next() && rs.getInt(1) > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Could not check room availability: " + e.getMessage(), e);
        }
    }
}