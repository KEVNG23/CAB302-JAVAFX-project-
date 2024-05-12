package com.example.demo1.Models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The SqliteConnection class provides a singleton connection to a SQLite database.
 * It initializes the connection to the database using JDBC and provides a static method to retrieve the instance.
 */
public class SqliteConnection {

    /** The singleton instance of the database connection. */
    private static Connection instance = null;

    /** Private constructor to prevent instantiation from outside the class. */
    private SqliteConnection() {
        String url = "jdbc:sqlite:screentrack.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    /**
     * Retrieves the singleton instance of the database connection.
     * If the instance is not initialized, it creates a new connection.
     *
     * @return The singleton instance of the database connection.
     */
    public static Connection getInstance() {
        if (instance == null) {
            new SqliteConnection();
        }
        return instance;
    }
}
