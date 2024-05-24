package com.example.demo1.HomePageController;

/**
 * Import statements.
 */
import com.example.demo1.AccountModel.Session;
import com.example.demo1.Models.SqliteConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The SqliteTaskDAO class implements the ITaskDAO interface to provide
 * methods for managing tasks in a SQLite database.
 */
public class SqliteTaskDAO implements ITaskDAO {

    private Connection connection;
    private Session accountId;

    /**
     * Constructs a new SqliteTaskDAO instance, initializes the database connection,
     * and creates the tasks table if it does not exist.
     */
    public SqliteTaskDAO() {
        System.out.println("Initializing SqliteTaskDAO and creating table.");
        connection = SqliteConnection.getInstance();
        createTable();
        // Used for testing
        // insertSampleData();
    }

    /**
     * Creates the tasks table in the database if it does not already exist.
     */
    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS tasks ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "task VARCHAR NOT NULL,"
                    + "time VARCHAR NOT NULL,"
                    + "urgency VARCHAR NOT NULL,"
                    + "account_id INTEGER NOT NULL,"
                    + "FOREIGN KEY (account_id) REFERENCES accounts(id)"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new task to the database.
     * @param task The task to be added.
     * @param session The current user session.
     */
    @Override
    public void addTask(Task task, Session session) {
        int accountId = session.getLoggedInAccount().getId();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO tasks (task, time, urgency, account_id) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, task.getTask());
            statement.setString(2, task.getTimeFrame());
            statement.setString(3, task.getUrgency());
            statement.setInt(4, accountId);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes an existing task from the database.
     * @param task The task to be removed.
     * @param session The current user session.
     */
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

    /**
     * Retrieves all tasks associated with a specific account ID.
     * @param accountId The ID of the account whose tasks are to be retrieved.
     * @return A list of tasks associated with the given account ID.
     */
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
