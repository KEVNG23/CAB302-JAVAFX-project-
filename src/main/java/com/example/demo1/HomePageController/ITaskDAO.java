package com.example.demo1.HomePageController;

import com.example.demo1.AccountModel.Account;
import com.example.demo1.AccountModel.Session;

import java.sql.Connection;
import java.util.List;

public interface ITaskDAO {



    public void addTask(Task task, Session session);

    public void removeTask(Task task, Session session);

    public List<Task> getAllTasks(int accountId);




}
