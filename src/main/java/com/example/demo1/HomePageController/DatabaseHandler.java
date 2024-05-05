package com.example.demo1.HomePageController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.demo1.Models.SqliteConnection;

public class DatabaseHandler {
    private Connection connection;

    public void connect() throws SQLException {
        connection = SqliteConnection.getInstance();
        System.out.println("Connected to the database.");
    }

    public String getUsername(String email, String password) throws SQLException {
        String username = null;
        String sql = "SELECT username FROM accounts WHERE email = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            // Check if ResultSet has a row
            if (rs.next()) {
                username = rs.getString("username");
                System.out.println("Retrieved username from database: " + username); // Debug statement
            } else {
                System.out.println("No username found in database for email: " + email + " and password: " + password); // Debug statement
            }
        } catch (SQLException e) {
            System.err.println("Error executing database query: " + e.getMessage()); // Error handling
        }
        return username;
    }

    public String getUsernameForLoggedInUser() throws SQLException {
        String username = null;
        // Implement the query to retrieve the username for the user with ID 1
        String query = "SELECT username FROM accounts WHERE id = 1"; // Assuming user ID 1 is the user you want to display the username for

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();

            // Check if ResultSet has a row
            if (rs.next()) {
                username = rs.getString("username").trim(); // Trim the whitespace
            }
        }
        return username;
    }


    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("Disconnected from the database.");
        }
    }
}
