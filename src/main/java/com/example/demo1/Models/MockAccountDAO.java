package com.example.demo1.Models;

import java.util.ArrayList;
import java.util.List;

public class MockAccountDAO implements IAccountDAO {
    private List<Account> accounts = new ArrayList<>();
    public MockAccountDAO() {
        accounts.add(new Account(1,"test1","test@email.com","test123"));
        accounts.add(new Account(2,"test2","test2@email.com","test123"));

    }

    @Override
    public void addAccount(Account account) {
        this.accounts.add(account);

    }

    @Override
    public void updateAccount(Account account) {

    }

    @Override
    public void deleteAccount(Account account) {

    }

    @Override
    public Account getAccount(String username) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }


    @Override
    public List<Account> getAllAccounts() {
        return null;
    }
}