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
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.date = new SimpleObjectProperty<>(date);
        this.priority = new SimpleStringProperty(priority);
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
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public String getPriority() {
        return priority.get();
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }
}

