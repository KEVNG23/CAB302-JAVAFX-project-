package com.example.demo1.Calendar;

import com.example.demo1.AccountModel.Session;
import com.example.demo1.Models.SqliteConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SqliteCalendarDAO implements ICalendarDAO {

    private Connection connection;
    private Session session;

    /**
     * Constructs a new SqliteCalendarDAO and initializes the database.
     */
    public SqliteCalendarDAO() {
        System.out.println("Initializing SqliteAccountDAO and creating table.");
        connection = SqliteConnection.getInstance();
        createTable();
        alterTable();
        // Used for testing
        //insertSampleData();

    }

    /**
     * Creates the "Activity" table in the database if it doesn't already exist.
     * This table stores calendar activities.
     */
    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Activity ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "title VARCHAR NOT NULL,"
                    + "date TEXT NOT NULL,"
                    + "priority VARCHAR NOT NULL,"
                    + "account_id INTEGER NOT NULL," // Column for the associated account ID
                    + "FOREIGN KEY (account_id) REFERENCES accounts(id)"// Foreign key constraint
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Alters the "Activity" table to add the "account_id" column if it doesn't exist.
     * This column is used to associate activities with user accounts.
     */
    @Override
    public void alterTable() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, "Activity", "account_id");
            if (!resultSet.next()) {
                // account_id column does not exist, add it
                Statement statement = connection.createStatement();
                String query = "ALTER TABLE Activity ADD COLUMN account_id INTEGER";
                statement.execute(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new activity to the "Activity" table in the database.
     *
     * @param calendarActivity The activity to add.
     * @param session          The session containing user information.
     */
    @Override
    public void addActivity(CalendarActivity calendarActivity, Session session) {
        int accountId = session.getLoggedInAccount().getId(); //Session class
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Activity (title, date, priority, account_id) VALUES (?, ?, ?, ?)");
            statement.setString(1, calendarActivity.getTitle());
            statement.setDate(2, java.sql.Date.valueOf(calendarActivity.getDate()));
            statement.setString(3, calendarActivity.getPriority());
            statement.setInt(4, accountId);
            statement.executeUpdate();
            ResultSet generateKeys = statement.getGeneratedKeys();
            if(generateKeys.next()){
                calendarActivity.setId(generateKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing activity in the "Activity" table.
     *
     * @param calendarActivity The activity to update.
     */
    @Override
    public void updateActivity(CalendarActivity calendarActivity) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Activity SET title = ?, date = ?, priority =? WHERE id = ?");
            statement.setString(1, calendarActivity.getTitle());
            statement.setDate(2, Date.valueOf(calendarActivity.getDate()));
            statement.setString(3, calendarActivity.getPriority());
            statement.setInt(4, calendarActivity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();        }
    }

    /**
     * Deletes an activity from the "Activity" table.
     *
     * @param calendarActivity The activity to delete.
     */
    @Override
    public void deleteActivity(CalendarActivity calendarActivity) {
        try {
            String query = "DELETE FROM Activity WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, calendarActivity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all activities associated with a specific account from the database.
     *
     * @param accountId The ID of the account.
     * @return A list of activities for the specified account.
     */
    @Override
    public List<CalendarActivity> getAllActivity(int accountId) {
        List<CalendarActivity> calendarActivities = new ArrayList<>();
        try {
            String query = "SELECT * FROM Activity WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
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
