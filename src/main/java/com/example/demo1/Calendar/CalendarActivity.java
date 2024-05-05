package com.example.demo1.Calendar;

import java.time.LocalDate;

public class CalendarActivity {
    private Integer id;
    private String title;
    private LocalDate date;
    private String priority;

    public CalendarActivity(Integer id, String title, LocalDate date, String priority) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.priority = priority;
    }

    public Integer getId() { return id;}

    public void setId(Integer id){
        this.id = id;
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
