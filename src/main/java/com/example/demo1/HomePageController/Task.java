package com.example.demo1.HomePageController;

public class Task {
    private String task;
    private String timeFrame;
    private String urgency;
    private Integer id;

    public Task(Integer id, String task, String timeFrame, String urgency) {
        this.id = id;
        this.task = task;
        this.timeFrame = timeFrame;
        this.urgency = urgency;
    }

    public Integer getId() { return id;}

    public void setId(Integer id) { this.id = id; }
    // Getters for task details
    public String getTask() {
        return task;
    }

    public void setTask(String task) { this.task = task; }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) { this.timeFrame = timeFrame; }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {  this.urgency = urgency; }
}
