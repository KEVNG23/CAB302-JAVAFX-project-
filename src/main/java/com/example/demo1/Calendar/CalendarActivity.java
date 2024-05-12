package com.example.demo1.Calendar;

import javafx.scene.Scene;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class CalendarActivity {
    private IntegerProperty id;
    private StringProperty title;
    private ObjectProperty<LocalDate> date;
    private StringProperty priority;

    public CalendarActivity(Integer id, String title, LocalDate date, String priority) {
        this.id = new SimpleIntegerProperty(id != null ? id : 0);
        this.title = new SimpleStringProperty(title != null ? title : "");
        this.date = new SimpleObjectProperty<>(date);
        this.priority = new SimpleStringProperty(priority != null ? priority : "");
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    // Getters and setters for non-property fields
    public Integer getId() {
        return id != null ? id.get() : null;
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title != null ? title.get() : null;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public LocalDate getDate() {
        return date != null ? date.get() : null;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public String getPriority() {
        return priority != null ? priority.get() : null;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }
}

