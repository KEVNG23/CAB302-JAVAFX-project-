package com.example.demo1.Models;

import com.example.demo1.Models.Account;

import java.util.List;

public interface IAccountDAO {
    public void addAccount(Account account);

    public void updateAccount(Account account);

    public void deleteAccount(Account account);

    public Account getAccount(String username);

    public List<Account> getAllAccounts();
}