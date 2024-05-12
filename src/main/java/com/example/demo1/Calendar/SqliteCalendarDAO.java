package com.example.demo1.Calendar;

import com.example.demo1.Models.SqliteConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SqliteCalendarDAO implements ICalendarDAO {

    private Connection connection;

    public SqliteCalendarDAO() {
        System.out.println("Initializing SqliteAccountDAO and creating table.");
        connection = SqliteConnection.getInstance();
        createTable();
        // Used for testing
        //insertSampleData();

    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Activity ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "title VARCHAR NOT NULL,"
                    + "date VARCHAR NOT NULL,"
                    + "priority VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void addActivity(CalendarActivity calendarActivity) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO activity (title, date, priority) VALUES (?, ?, ?)");
            statement.setString(1, calendarActivity.getTitle());
            statement.setDate(2, java.sql.Date.valueOf(calendarActivity.getDate()));
            statement.setString(3, calendarActivity.getPriority());
            statement.executeUpdate();
            ResultSet generateKeys = statement.getGeneratedKeys();
            if(generateKeys.next()){
                calendarActivity.setId(generateKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateActivity(CalendarActivity calendarActivity) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE activity SET title = ?, date = ?, priority =? WHERE id = ?");
            statement.setString(1, calendarActivity.getTitle());
            statement.setDate(2, Date.valueOf(calendarActivity.getDate()));
            statement.setString(3, calendarActivity.getPriority());
            statement.setInt(4, calendarActivity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();        }
    }

    @Override
    public void deleteActivity(CalendarActivity calendarActivity) {
        try {
            String query = "DELETE FROM activity WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, calendarActivity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CalendarActivity> getAllActivity() {
        List<CalendarActivity> calendarActivities = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM activity";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                long millis = resultSet.getLong("date"); // Assuming date is stored as milliseconds
                LocalDate date = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000)); // Convert milliseconds to LocalDate
                String priority = resultSet.getString("priority");
                CalendarActivity calendarActivity = new CalendarActivity(id, title, date, priority);
                calendarActivities.add(calendarActivity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calendarActivities;
    }

}
