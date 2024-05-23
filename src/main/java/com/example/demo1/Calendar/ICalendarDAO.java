package com.example.demo1.Calendar;

import com.example.demo1.AccountModel.Session;

import java.util.List;

/**
 * Interface for Calendar Data Access Object.
 */
public interface ICalendarDAO {
    /**
     * Adds an activity to the calendar.
     *
     * @param calendarActivity The activity to add.
     * @param session The session containing user information.
     */
    public void addActivity(CalendarActivity calendarActivity, Session session);

    /**
     * Alters the calendar table if necessary.
     */
    public void alterTable();

    /**
     * Updates an existing activity in the calendar.
     *
     * @param calendarActivity The activity to update.
     */
    public void updateActivity(CalendarActivity calendarActivity);

    /**
     * Deletes an activity from the calendar.
     *
     * @param calendarActivity The activity to delete.
     */
    public void deleteActivity(CalendarActivity calendarActivity);

    /**
     * Retrieves all activities for a specific account.
     *
     * @param accountId The account ID.
     * @return A list of activities for the account.
     */
    public List<CalendarActivity> getAllActivity(int accountId);
}
