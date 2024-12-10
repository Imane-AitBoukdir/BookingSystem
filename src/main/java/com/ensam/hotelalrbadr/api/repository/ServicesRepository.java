package com.ensam.hotelalrbadr.api.repository;

import com.ensam.hotelalrbadr.api.config.DatabaseConfig;
import com.ensam.hotelalrbadr.api.model.Services;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicesRepository {
    private final DatabaseConfig dbConfig;

    public ServicesRepository() {
        this.dbConfig = DatabaseConfig.getInstance();
    }

    public List<Services> findAll() {
        List<Services> services = new ArrayList<>();
        String sql = "SELECT * FROM services";

        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Services service = new Services();
                service.setId(rs.getLong("id"));  // Note: using setId with Long parameter
                service.setName(rs.getString("name"));
                service.setIconUrl(rs.getString("icon_url"));
                services.add(service);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching services", e);
        }
        return services;
    }

    public List<Services> findByRoomId(Long roomId) {
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
            throw new RuntimeException("Error fetching services for room", e);
        }
        return services;
    }

    public Services findById(Long id) {
        String sql = "SELECT * FROM services WHERE id = ?";

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Services service = new Services();
                service.setId(rs.getLong("id"));
                service.setName(rs.getString("name"));
                service.setIconUrl(rs.getString("icon_url"));
                return service;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching service by id", e);
        }
        return null;
    }
}