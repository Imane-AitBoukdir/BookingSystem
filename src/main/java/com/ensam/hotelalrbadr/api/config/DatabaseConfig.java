package com.ensam.hotelalrbadr.api.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Alae2004";

    private static DatabaseConfig instance;

    private DatabaseConfig() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL driver", e);
        }
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public boolean testConnection() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Test basic connectivity
            System.out.println("Database connection successful!");

            // Test if rooms table exists and get count
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM rooms")) {
                if (rs.next()) {
                    int roomCount = rs.getInt(1);
                    System.out.println("Found " + roomCount + " rooms in database");
                }
            } catch (SQLException e) {
                System.err.println("Error querying rooms table: " + e.getMessage());
                return false;
            }

            // Test if required tables exist
            String[] requiredTables = {"rooms", "users", "reservations"};
            for (String table : requiredTables) {
                try (ResultSet rs = conn.getMetaData().getTables(null, null, table, null)) {
                    if (!rs.next()) {
                        System.err.println("Required table missing: " + table);
                        return false;
                    }
                }
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}