package com.example.demo1.AccountModel;

import com.example.demo1.AccountModel.Account;

import java.util.List;

public interface IAccountDAO {
    public void addAccount(Account account);

    public void updateAccount(Account account);

    public void deleteAccount(Account account);

    public Account getAccount(String username);

    public List<Account> getAllAccounts();
}