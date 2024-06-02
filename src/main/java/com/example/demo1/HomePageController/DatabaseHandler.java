/**
 * Previously used file but was made redundant.
 * Uncomment all lines to preview code.
 */

//package com.example.demo1.HomePageController;
//
///**
// * Import statements.
// */
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import com.example.demo1.Models.SqliteConnection;
//
///**
// * The DatabaseHandler class manages database connections and operations.
// * It provides methods to connect to the database, retrieve usernames, and close the connection.
// */
//public class DatabaseHandler {
//    private Connection connection;
//
//    /**
//     * Establishes a connection to the database.
//     * @throws SQLException if a database access error occurs.
//     */
//    public void connect() throws SQLException {
//        connection = SqliteConnection.getInstance();
//        System.out.println("Connected to the database.");
//    }
//
//    /**
//     * Retrieves the username for the given email and password.
//     * @param email The email of the user.
//     * @param password The password of the user.
//     * @return The username if found, otherwise null.
//     * @throws SQLException if a database access error occurs.
//     */
//    public String getUsername(String email, String password) throws SQLException {
//        String username = null;
//        String sql = "SELECT username FROM accounts WHERE email = ? AND password = ?";
//        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
//            pstmt.setString(1, email);
//            pstmt.setString(2, password);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                username = rs.getString("username");
//                System.out.println("Retrieved username from database: " + username);
//            } else {
//                System.out.println("No username found in database for email: " + email + " and password: " + password); // Debug statement
//            }
//        } catch (SQLException e) {
//            System.err.println("Error executing database query: " + e.getMessage());
//        }
//        return username;
//    }
//
//    /**
//     * Retrieves the username for the user with ID 1.
//     * This method is intended to display the username for a specific user.
//     * @return The username if found, otherwise null.
//     * @throws SQLException if a database access error occurs.
//     */
//    public String getUsernameForLoggedInUser() throws SQLException {
//        String username = null;
//        String query = "SELECT username FROM accounts WHERE id = 1";
//
//        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
//            ResultSet rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                username = rs.getString("username").trim();
//            }
//        }
//        return username;
//    }
//
//    /**
//     * Closes the database connection.
//     * @throws SQLException if a database access error occurs.
//     */
//    public void closeConnection() throws SQLException {
//        if (connection != null) {
//            connection.close();
//            System.out.println("Disconnected from the database.");
//        }
//    }
//}
