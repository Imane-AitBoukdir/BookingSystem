// Path: com/ensam/hotelalrbadr/api/repository/RoomServicesRepository.java

package com.ensam.hotelalrbadr.api.repository;

import com.ensam.hotelalrbadr.api.config.DatabaseConfig;
import com.ensam.hotelalrbadr.api.model.Services;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomServicesRepository {
    private final DatabaseConfig dbConfig;

    public RoomServicesRepository() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    // Get all services for a specific room
    public List<Services> findServicesByRoomId(Long roomId) {
        List<Services> servicesList = new ArrayList<>();
        String sql = """
            SELECT s.* 
            FROM services s
            JOIN room_services rs ON s.service_id = rs.service_id
            WHERE rs.room_id = ?
        """;

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, roomId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                servicesList.add(mapResultSetToService(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching services for room: " + e.getMessage());
        }
        return servicesList;
    }

    // Helper method to map ResultSet to services object
    private Services mapResultSetToService(ResultSet rs) throws SQLException {
        return new Services(
                rs.getLong("service_id"),
                rs.getString("service_name"),
                rs.getString("description"),
                rs.getString("icon_url")
        );
    }

    // Optional: Add service to room
    public void addServiceToRoom(Long roomId, Long serviceId) {
        String sql = "INSERT INTO room_services (room_id, service_id) VALUES (?, ?)";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, roomId);
            pstmt.setLong(2, serviceId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding service to room: " + e.getMessage());
        }
    }

    // Optional: Remove service from room
    public void removeServiceFromRoom(Long roomId, Long serviceId) {
        String sql = "DELETE FROM room_services WHERE room_id = ? AND service_id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, roomId);
            pstmt.setLong(2, serviceId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error removing service from room: " + e.getMessage());
        }
    }
}