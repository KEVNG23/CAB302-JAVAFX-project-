package com.example.demo1.AccountModel;

import java.util.Arrays;
import java.util.List;

/**
 * The Account class represents a user account with basic information such as username, email, and password.
 */
public class Account {
    private Integer id;
    private String username;

    private String email;

    private String password;

    // List of valid email domains
    private static final List<String> VALID_DOMAINS = Arrays.asList("gmail.com", "yahoo.com", "outlook.com");

    /**
     * Constructs an Account object with the specified parameters.
     * @param id The unique identifier of the account.
     * @param username The username of the account.
     * @param email The email associated with the account.
     * @param password The password of the account.
     */
    public Account(Integer id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Gets the ID of the account.
     * @return The ID of the account.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the account.
     * @param id The ID to set.
     */
    public void setId(Integer id){
        this.id = id;
    }

    /**
     * Gets the username of the account.
     * @return The username of the account.
     */
    public String getUsername (){
        return username;
    }

    /**
     * Sets the username of the account.
     * @param username The username to set.
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Gets the email associated with the account.
     * @return The email associated with the account.
     */
    public String getEmail(){
        return email;
    }

    /**
     * Sets the email associated with the account.
     * @param email The email to set.
     * @throws IllegalArgumentException if the email is not a valid address from the allowed domains.
     */
    public void setEmail(String email) {
        if (email != null && !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address. The email must be from a valid domain: " + VALID_DOMAINS);
        }
        this.email = email;
    }

    /**
     * Gets the password of the account.
     * @return The password of the account.
     */
    public String getPassword(){return password;}

    /**
     * Sets the password of the account.
     * @param password The password to set.
     */
    public void setPassword(String password){this.password = password;}


    /**
     * Validates if the email has a valid domain.
     * @param email The email to validate.
     * @return true if the email is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        for (String domain : VALID_DOMAINS) {
            if (email.endsWith("@" + domain)) {
                return true;
            }
        }
        return false;
    }




}
