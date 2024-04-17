package com.example.demo1.Models;

public class MockAccount {

    public static Account generateMockAccount(String username) {
        if ("testUser".equals(username)) {
            return new Account("testUser", "test@example.com", "$2a$10$uwnnwJKdPT6Ex0tKtX4DnOY1xU8DcZ3r/IZXIHse6bsUqVhTByhuW");
        } else {
            return null;
        }
    }
}
