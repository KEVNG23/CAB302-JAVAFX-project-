package com.example.demo1.Models;

import org.mindrot.jbcrypt.BCrypt;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SqliteAccountDAO implements IAccountDAO{
    private Connection connection;

    /**
     *
     */
    public SqliteAccountDAO() {
        System.out.println("Initializing SqliteAccountDAO and creating table.");
        connection = SqliteConnection.getInstance();
        createTable();
        // Used for testing
        //insertSampleData();

    }

    private void insertSampleData(){
        try {
            //clear before inserting
            Statement clearStatement = connection.createStatement();
            String clearQuery = "DELETE FROM accounts";
            clearStatement.execute(clearQuery);
        }catch (SQLException e) {
            System.err.println("Error clearing accounts table: " + e.getMessage());
            e.printStackTrace();
        }
            String insertQuery = "INSERT INTO accounts (username, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)){


            String[][] sampleData= {
                    {"test4", "test4@email.com", "test123"},
                    {"test5", "test5@email.com", "test123"},
                    {"test6", "test6@email.com", "test123"}
            };
            for (String[] data :sampleData){
                Account accountSample = new Account(null, data[0], data[1], data[2]);
                insertStatement.setString(1, accountSample.getUsername()); // Username
                insertStatement.setString(2, accountSample.getEmail()); // Email
                insertStatement.setString(3, accountSample.getPassword()); // Hashed Password
                insertStatement.executeUpdate();
            }
        } catch (SQLException e){
            System.err.println("Error inserting sample data: " + e.getMessage());
            e.printStackTrace();
        }
    }



    /**
     *
     */
    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS accounts ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "username VARCHAR NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }



    @Override
    public void addAccount(Account account) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO accounts (username, email, password) VALUES (?, ?, ?)");
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getEmail());
            statement.setString(3, account.getPassword());
            statement.executeUpdate();
            ResultSet generateKeys = statement.getGeneratedKeys();
            if(generateKeys.next()){
                account.setId(generateKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void updateAccount(Account account) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET username = ?, email = ?, password =? WHERE id = ?");
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getEmail());
            statement.setString(3, account.getPassword());
            statement.setInt(4, account.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();        }

    }

    @Override
    public void deleteAccount(Account account) {
        try {
            String query = "DELETE FROM accounts WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, account.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Account getAccount(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM accounts WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                return new Account(id, username, email, password);
            } else
            {
                System.out.println("No account found for username: " + username);  // Log if no account found
            }

        } catch (Exception e) {
            System.err.println("General error in getAccount: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM accounts";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString ("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Account account = new Account(id, username, email, password);
                account.setId(id);
                accounts.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }


}

