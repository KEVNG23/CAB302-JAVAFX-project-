package com.example.demo1.AccountModel;

/**
 * Singleton class to manage the session of the currently logged-in user.
 * This class stores the details of the logged-in account and provides a global access point to it.
 */
public class Session {
    // Singleton instance of the Session class
    private static Session instance;

    // Stores the currently logged-in account details
    private Account loggedInAccount;

    // Private constructor to prevent instantiation from outside the class
    private Session() {}

    /**
     * Returns the single instance of the Session class.
     * If the instance does not exist, it will create and return a new one.
     *
     * @return the singleton instance of the Session class
     */
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    /**
     * Gets the account currently logged into the session.
     *
     * @return the logged-in Account object, or null if no account is logged in
     */
    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    /**
     * Sets the account as the currently logged-in account for this session.
     *
     * @param loggedInAccount the Account object to be set as the currently logged-in account
     */
    public void setLoggedInAccount(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }
}
