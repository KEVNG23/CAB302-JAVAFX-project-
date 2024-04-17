package com.example.demo1.Models;

import org.mindrot.jbcrypt.BCrypt;

public class Account {
    private int id;
    private String username;

    private String email;

    private String passwordHash;
    public Account (String username, String email, String password ){
        this.username = username;
        this.email = email;
        this.passwordHash = hashPassword(password);
    }

    public int getId(){
        return id;
    }

    public void setId(){
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

    public boolean verifyPassword(String password ){
        return BCrypt.checkpw(password, this.passwordHash);
    }
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


}
