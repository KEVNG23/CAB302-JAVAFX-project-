package com.example.demo1.Calendar;

import com.example.demo1.AccountModel.Session;

import java.util.List;


public interface ICalendarDAO {
    public void addActivity(CalendarActivity calendarActivity, Session session);

    public void alterTable();


    public void updateActivity(CalendarActivity calendarActivity);

    public void deleteActivity(CalendarActivity calendarActivity);

    public List<CalendarActivity> getAllActivity(int accountId);
}
