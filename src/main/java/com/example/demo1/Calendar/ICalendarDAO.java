package com.example.demo1.Calendar;

import java.util.List;


public interface ICalendarDAO {
    public void addActivity(CalendarActivity calendarActivity);

    public void updateActivity(CalendarActivity calendarActivity);

    public void deleteActivity(CalendarActivity calendarActivity);

    public List<CalendarActivity> getAllActivity();
}
