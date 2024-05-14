package com.example.demo1.HomePageController;
import com.example.demo1.AccountModel.Session;
import com.example.demo1.Models.SqliteConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteTaskDAO implements ITaskDAO {

    private Connection connection;

    private Session accountId;

    public SqliteTaskDAO() {
        System.out.println("Initializing SqliteAccountDAO and creating table.");
        connection = SqliteConnection.getInstance();
        createTable();
        // Used for testing
        //insertSampleData();

    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS tasks ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "task VARCHAR NOT NULL,"
                    + "time VARCHAR NOT NULL,"
                    + "urgency VARCHAR NOT NULL,"
                    + "account_id INTEGER NOT NULL," // Column for the associated account ID
                    + "FOREIGN KEY (account_id) REFERENCES accounts(id)"// Foreign key constraint
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void addTask(Task task, Session session) {
        int accountId = session.getLoggedInAccount().getId(); //Session class
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tasks (task, time, urgency, account_id) VALUES (?, ?, ?, ?)");
            statement.setString(1, task.getTask());
            statement.setString(2, task.getTimeFrame());
            statement.setString(3, task.getUrgency());
            statement.setInt(4, accountId);
            statement.executeUpdate();
            ResultSet generateKeys = statement.getGeneratedKeys();
            if(generateKeys.next()){
                task.setId(generateKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeTask(Task task, Session session) {
        try {
            String query = "DELETE FROM tasks WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Task> getAllTasks(int accountId) {
        List<Task> tasks = new ArrayList<>();
        try {
            String query = "SELECT * FROM tasks WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String taskText = resultSet.getString("task");
                String time = resultSet.getString("time");
                String urgency = resultSet.getString("urgency");
                Task task = new Task(id, taskText, time, urgency);
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }


}
