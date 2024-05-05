package com.example.demo1.Calendar;

import java.util.List;


public interface ICalendarDAO {
    public void addActivity(CalendarActivity activity);

    public void updateActivity(CalendarActivity activity);

    public void deleteActivity(CalendarActivity activity);

    public List<CalendarActivity> getAllActivity();
}
