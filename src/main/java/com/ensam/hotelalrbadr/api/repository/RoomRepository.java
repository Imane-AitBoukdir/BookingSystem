package com.ensam.hotelalrbadr.api.repository;

import com.ensam.hotelalrbadr.api.config.DatabaseConfig;
import com.ensam.hotelalrbadr.api.model.Room;
import com.ensam.hotelalrbadr.api.model.Services;  // Updated import
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {
    private final DatabaseConfig dbConfig;

    public RoomRepository() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    // Method to fetch all rooms from the database
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Room room = mapResultSetToRoom(rs);
                // For each room, fetch its associated services
                room.setServices(getRoomServices(room.getId()));
                rooms.add(room);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching rooms", e);
        }
        return rooms;
    }

    // Method to fetch rooms by category
    public List<Room> findByCategory(String category) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE category = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Room room = mapResultSetToRoom(rs);
                // For each room, fetch its associated services
                room.setServices(getRoomServices(room.getId()));
                rooms.add(room);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching rooms by category", e);
        }
        return rooms;
    }

    // Helper method to map database results to Room object
    private Room mapResultSetToRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getLong("id"));
        room.setName(rs.getString("name"));
        room.setDescription(rs.getString("description"));
        room.setPrice(rs.getDouble("price"));
        room.setImageUrl(rs.getString("image_url"));
        room.setCategory(rs.getString("category"));
        return room;
    }

    // Method to fetch services associated with a room
    private List<Services> getRoomServices(Long roomId) {
        List<Services> services = new ArrayList<>();
        String sql = """
            SELECT s.* FROM services s
            JOIN room_services rs ON s.id = rs.service_id
            WHERE rs.room_id = ?
        """;

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, roomId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Services service = new Services();
                service.setId(rs.getLong("id"));
                service.setName(rs.getString("name"));
                service.setIconUrl(rs.getString("icon_url"));
                services.add(service);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching room services", e);
        }
        return services;
    }
}