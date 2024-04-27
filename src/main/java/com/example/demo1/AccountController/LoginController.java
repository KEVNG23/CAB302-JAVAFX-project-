package com.example.demo1.AccountController;

import com.example.demo1.AccountModel.SqliteAccountDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.demo1.Main;
import javafx.fxml.FXML;
import com.example.demo1.AccountModel.Account;

import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink registerLink;


    private SqliteAccountDAO accountDAO;


    @FXML
    public void initialize(){
        this.accountDAO = new SqliteAccountDAO();
        loginButton.setOnAction(event -> onLoginButtonClick());
        registerLink.setOnAction(event -> onRegisterLinkClick());
        usernameField.setOnAction(event -> onEnterKeyPressed());
        passwordField.setOnAction(event -> onEnterKeyPressed());
    }

    @FXML
    protected void onEnterKeyPressed(){
        onLoginButtonClick();
    }


    @FXML
    protected void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean isLoggedIn = authenticateUser (username, password);
        SqliteAccountDAO accountDAO = new SqliteAccountDAO();

        if(!existedAccount(username, accountDAO.getAllAccounts())) {
            showErrorAlert("Can't find this account");
            return;
        }
        if (isLoggedIn) {
            navigationToHomepage();
        } else {
            showErrorAlert("Incorrect username or password.");

        }


    }
    protected boolean existedAccount(String username, List<Account> accounts) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }



    @FXML
    protected void navigationToHomepage() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), Main.WIDTH, Main.HEIGHT); //change to Homepage size
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
    }

    @FXML
    protected void onRegisterLinkClick(){
        Stage stage = (Stage) registerLink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("register-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), Main.WIDTH, Main.HEIGHT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);

    }


    /**
     *
     * @param username
     * @param password
     * @return
     */
    public boolean authenticateUser(String username, String password) {
        System.out.println("Attempting to authenticate user: " + username);
        Account accountData = accountDAO.getAccount(username);
        if (accountData != null) {

            String isPasswordCorrect = accountData.getPassword();
            if (isPasswordCorrect.equals(password)) {
                System.out.println("Password verified. Authentication successful.");
                return true;
            } else {
                System.out.println("Incorrect password for username: " + username);
            }
        } else {
            System.out.println("No account found with username: " + username);
        }

        return false;
    }





    /**
     *
     * @param message
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert (Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}