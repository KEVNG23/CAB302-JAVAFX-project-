package com.example.demo1.Models;

import java.time.LocalDate;

public class Activity {
    private String title;
    private LocalDate date;
    private String priority;

    public Activity(String title, LocalDate date, String priority) {
        this.title = title;
        this.date = date;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
