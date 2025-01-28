package com.ensam.hotelalrbadr.api.repository;

import com.ensam.hotelalrbadr.api.config.DatabaseConfig;
import com.ensam.hotelalrbadr.api.model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    // Get all rooms for a specific category (luxury, family, or economy)
    public List<Room> findByCategory(String category) {
        List<Room> rooms = new ArrayList<>();

        try {
            // Get database connection
            Connection conn = DatabaseConfig.getInstance().getConnection();

            // Create and execute query
            String sql = "SELECT * FROM rooms WHERE category = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            // Convert each database row to a Room object
            while (rs.next()) {
                Room room = new Room();

                // Set basic room information
                room.setId(rs.getLong("id"));
                room.setName(rs.getString("name"));
                room.setDescription(rs.getString("description"));
                room.setPrice(rs.getDouble("price"));
                room.setCategory(rs.getString("category"));

                // Set image paths
                room.setListViewImage(rs.getString("list_view_image"));
                room.setDetailViewImage(rs.getString("detail_view_image"));
                room.setReservationImage(rs.getString("reservation_image"));

                // Set creation timestamp
                room.setCreatedAt(rs.getTimestamp("created_at"));

                // Add room to list
                rooms.add(room);
            }

            // Clean up resources
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Error getting rooms: " + e.getMessage());
        }

        return rooms;
    }

    // Get a single room by its ID
    public Room findById(Long roomId) {
        try {
            Connection conn = DatabaseConfig.getInstance().getConnection();

            String sql = "SELECT * FROM rooms WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, roomId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Room room = new Room();
                room.setId(rs.getLong("id"));
                room.setName(rs.getString("name"));
                room.setDescription(rs.getString("description"));
                room.setPrice(rs.getDouble("price"));
                room.setCategory(rs.getString("category"));
                room.setListViewImage(rs.getString("list_view_image"));
                room.setDetailViewImage(rs.getString("detail_view_image"));
                room.setReservationImage(rs.getString("reservation_image"));
                room.setCreatedAt(rs.getTimestamp("created_at"));

                rs.close();
                stmt.close();
                conn.close();

                return room;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Error finding room: " + e.getMessage());
        }

        return null;
    }
}