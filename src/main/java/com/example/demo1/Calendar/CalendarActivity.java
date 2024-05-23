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

/**
 * Represents an activity in the calendar.
 */
public class CalendarActivity {
    private IntegerProperty id;
    private StringProperty title;
    private ObjectProperty<LocalDate> date;
    private StringProperty priority;

    /**
     * Constructs a CalendarActivity with the specified id, title, date, and priority.
     *
     * @param id       the activity id
     * @param title    the activity title
     * @param date     the date of the activity
     * @param priority the priority of the activity
     */
    public CalendarActivity(Integer id, String title, LocalDate date, String priority) {
        this.id = new SimpleIntegerProperty(id != null ? id : 0);
        this.title = new SimpleStringProperty(title != null ? title : "");
        this.date = new SimpleObjectProperty<>(date);
        this.priority = new SimpleStringProperty(priority != null ? priority : "");
    }

    /**
     * Gets the id of the activity.
     *
     * @return the id of the activity
     */
    public Integer getId() {
        return id != null ? id.get() : null;
    }

    /**
     * Sets the id of the activity.
     *
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id.set(id);
    }

    /**
     * Gets the title of the activity.
     *
     * @return the title of the activity
     */
    public String getTitle() {
        return title != null ? title.get() : null;
    }

    /**
     * Sets the title of the activity.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title.set(title);
    }

    /**
     * Gets the date of the activity.
     *
     * @return the date of the activity
     */
    public LocalDate getDate() {
        return date != null ? date.get() : null;
    }

    /**
     * Sets the date of the activity.
     *
     * @param date the new date
     */
    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    /**
     * Gets the priority of the activity.
     *
     * @return the priority of the activity
     */
    public String getPriority() {
        return priority != null ? priority.get() : null;
    }

    /**
     * Sets the priority of the activity.
     *
     * @param priority the new priority
     */
    public void setPriority(String priority) {
        this.priority.set(priority);
    }
}
