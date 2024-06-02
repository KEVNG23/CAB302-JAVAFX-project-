package com.example.demo1.HomePageController;

/**
 * The Task class represents a task in the application.
 * It stores information about the task, including its ID, description, time frame, and urgency.
 */
public class Task {
    private String task; // The description of the task
    private String timeFrame; // The time frame for completing the task
    private String urgency; // The urgency level of the task
    private Integer id; // The unique identifier for the task

    /**
     * Constructs a Task object with the specified parameters.
     * @param id The ID of the task.
     * @param task The description of the task.
     * @param timeFrame The time frame for completing the task.
     * @param urgency The urgency level of the task.
     */
    public Task(Integer id, String task, String timeFrame, String urgency) {
        this.id = id;
        this.task = task;
        this.timeFrame = timeFrame;
        this.urgency = urgency;
    }

    /**
     * Retrieves the ID of the task.
     * @return The ID of the task.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the task.
     * @param id The ID of the task to be set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves the description of the task.
     * @return The description of the task.
     */
    public String getTask() {
        return task;
    }

    /**
     * Sets the description of the task.
     * @param task The description of the task to be set.
     */
    public void setTask(String task) {
        this.task = task;
    }

    /**
     * Retrieves the time frame for completing the task.
     * @return The time frame for completing the task.
     */
    public String getTimeFrame() {
        return timeFrame;
    }

    /**
     * Sets the time frame for completing the task.
     * @param timeFrame The time frame for completing the task to be set.
     */
    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    /**
     * Retrieves the urgency level of the task.
     * @return The urgency level of the task.
     */
    public String getUrgency() {
        return urgency;
    }

    /**
     * Sets the urgency level of the task.
     * @param urgency The urgency level of the task to be set.
     */
    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }
}
