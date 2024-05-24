package com.example.demo1.HomePageController;

/**
 * Import statements.
 */
import com.example.demo1.AccountModel.Session;
import java.util.List;

/**
 * The ITaskDAO interface provides methods for managing tasks in the application.
 * It includes methods to add, remove, and retrieve tasks.
 */
public interface ITaskDAO {

    /**
     * Adds a new task to the database.
     * @param task The task to be added.
     * @param session The current user session.
     */
    public void addTask(Task task, Session session);

    /**
     * Removes an existing task from the database.
     * @param task The task to be removed.
     * @param session The current user session.
     */
    public void removeTask(Task task, Session session);

    /**
     * Retrieves all tasks associated with a specific account ID.
     * @param accountId The ID of the account whose tasks are to be retrieved.
     * @return A list of tasks associated with the given account ID.
     */
    public List<Task> getAllTasks(int accountId);
}
