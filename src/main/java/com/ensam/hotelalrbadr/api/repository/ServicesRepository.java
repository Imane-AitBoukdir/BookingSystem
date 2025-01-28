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
        String sql = "SELECT * FROM services ORDER BY name";
        List<Services> services = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                services.add(mapResultSetToService(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching services", e);
        }
        return services;
    }

    public List<Services> findByRoomId(Long roomId) {
        String sql = """
            SELECT s.* FROM services s
            JOIN room_services rs ON s.id = rs.service_id
            WHERE rs.room_id = ?
            ORDER BY s.name
        """;
        List<Services> services = new ArrayList<>();

        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, roomId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                services.add(mapResultSetToService(rs));
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
            return rs.next() ? mapResultSetToService(rs) : null;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching service by id", e);
        }
    }

    public void save(Services service) {
        if (service.getId() == null) {
            insert(service);
        } else {
            update(service);
        }
    }

    private void insert(Services service) {
        String sql = "INSERT INTO services (name, icon_url) VALUES (?, ?)";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, service.getName());
            pstmt.setString(2, service.getIconUrl());

            if (pstmt.executeUpdate() > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        service.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving service", e);
        }
    }

    private void update(Services service) {
        String sql = "UPDATE services SET name = ?, icon_url = ? WHERE id = ?";
        try (Connection conn = dbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, service.getName());
            pstmt.setString(2, service.getIconUrl());
            pstmt.setLong(3, service.getId());

            if (pstmt.executeUpdate() == 0) {
                throw new SQLException("Updating service failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating service", e);
        }
    }

    private Services mapResultSetToService(ResultSet rs) throws SQLException {
        Services service = new Services();
        service.setId(rs.getLong("id"));
        service.setName(rs.getString("name"));
        service.setIconUrl(rs.getString("icon_url"));
        return service;
    }
}