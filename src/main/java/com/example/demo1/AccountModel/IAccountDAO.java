package com.example.demo1.AccountModel;

import com.example.demo1.AccountModel.Account;

import java.util.List;

/**
 * The IAccountDAO interface defines the contract for accessing and managing user accounts.
 */
public interface IAccountDAO {
    /**
     * Adds a new account to the data store.
     * @param account The account to add.
     */
    public void addAccount(Account account);

    /**
     * Updates an existing account in the data store.
     * @param account The account to update.
     */
    public void updateAccount(Account account);

    /**
     * Deletes an account from the data store.
     * @param account The account to delete.
     */
    public void deleteAccount(Account account);

    /**
     * Retrieves an account by its username.
     * @param username The username of the account to retrieve.
     * @return The account with the specified username, or null if not found.
     */
    public Account getAccount(String username);

    /**
     * Retrieves a list of all accounts stored in the data store.
     * @return A list of all accounts.
     */
    public List<Account> getAllAccounts();
}