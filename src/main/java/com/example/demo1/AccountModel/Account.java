package com.example.demo1.AccountModel;

import org.mindrot.jbcrypt.BCrypt;

public class Account {
    private Integer id;
    private String username;

    private String email;

    private String password;

    //private static final String SALT = BCrypt.gensalt();

    /**
     *
     * @param id
     * @param username
     * @param email
     * @param password
     */
    public Account (Integer id, String username, String email, String password){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }
    public String getUsername (){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}

    //public boolean verifyPassword(String plainPassword) {
    //    System.out.println("Stored Password Hash: " + passwordHash);
    //    boolean result = BCrypt.checkpw(plainPassword, passwordHash);
    //    System.out.println("Password Verification Result: " + result);
    //    return result;
    //}

    //private  String hashPassword(String plainPassword) {
    //    return BCrypt.hashpw(plainPassword, SALT);
    //}



}
