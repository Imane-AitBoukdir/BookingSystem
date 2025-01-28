package com.ensam.hotelalrbadr.api.repository;

import com.ensam.hotelalrbadr.api.config.DatabaseConfig;
import com.ensam.hotelalrbadr.api.model.Services;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all database operations related to room services.
 * It manages the relationship between rooms and their services.
 */
public class RoomServicesRepository {
    // Database connection
    private final DatabaseConfig dbConfig;

    /**
     * Creates a new repository instance
     */
    public RoomServicesRepository() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    /**
     * Gets all services assigned to a specific room
     *
     * @param roomId The ID of the room
     * @return List of services assigned to the room
     */
    public List<Services> getServicesForRoom(Long roomId) {
        // SQL query to get services for a room
        String sql =
                "SELECT s.* " +               // Get all service fields
                        "FROM services s " +          // From services table
                        "JOIN room_services rs " +    // Join with room_services table
                        "ON s.id = rs.service_id " +  // Match service IDs
                        "WHERE rs.room_id = ? " +     // For the specified room
                        "ORDER BY s.name";            // Sort by service name

        List<Services> services = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the room ID in the query
            stmt.setLong(1, roomId);

            // Execute query and get results
            ResultSet rs = stmt.executeQuery();

            // Convert each database row to a Service object
            while (rs.next()) {
                services.add(createServiceFromRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error getting services for room: " + e.getMessage(), e);
        }

        return services;
    }

    /**
     * Assigns a list of services to a room
     *
     * @param roomId The ID of the room
     * @param serviceIds List of service IDs to assign
     */
    public void addServicesToRoom(Long roomId, List<Long> serviceIds) {
        String sql = "INSERT INTO room_services (room_id, service_id) VALUES (?, ?)";

        try (Connection conn = dbConfig.getConnection()) {
            // Turn off auto-commit for better performance
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Add each service to the room
                for (Long serviceId : serviceIds) {
                    stmt.setLong(1, roomId);
                    stmt.setLong(2, serviceId);
                    stmt.addBatch();
                }

                // Execute all inserts
                stmt.executeBatch();
                // Save changes
                conn.commit();

            } catch (SQLException e) {
                // If something goes wrong, undo changes
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error adding services to room: " + e.getMessage(), e);
        }
    }

    /**
     * Removes all services from a room and assigns new ones
     *
     * @param roomId The ID of the room
     * @param serviceIds List of new service IDs to assign
     */
    public void updateRoomServices(Long roomId, List<Long> serviceIds) {
        try (Connection conn = dbConfig.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // First, remove all existing services
                removeAllServicesFromRoom(conn, roomId);

                // Then, add the new services
                if (!serviceIds.isEmpty()) {
                    addNewServicesToRoom(conn, roomId, serviceIds);
                }

                // Save all changes
                conn.commit();

            } catch (SQLException e) {
                // If something goes wrong, undo changes
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating room services: " + e.getMessage(), e);
        }
    }

    /**
     * Removes all services from a room
     */
    private void removeAllServicesFromRoom(Connection conn, Long roomId) throws SQLException {
        String sql = "DELETE FROM room_services WHERE room_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, roomId);
            stmt.executeUpdate();
        }
    }

    /**
     * Adds new services to a room
     */
    private void addNewServicesToRoom(Connection conn, Long roomId, List<Long> serviceIds) throws SQLException {
        String sql = "INSERT INTO room_services (room_id, service_id) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Long serviceId : serviceIds) {
                stmt.setLong(1, roomId);
                stmt.setLong(2, serviceId);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    /**
     * Creates a Service object from a database row
     */
    private Services createServiceFromRow(ResultSet rs) throws SQLException {
        Services service = new Services();
        service.setId(rs.getLong("id"));
        service.setName(rs.getString("name"));
        service.setIconUrl(rs.getString("icon_url"));
        return service;
    }
}