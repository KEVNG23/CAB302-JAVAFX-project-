package com.example.demo1.AccountController;

import com.example.demo1.AccountModel.Session;
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

import java.util.prefs.Preferences;

/**
 * The LoginController class manages the functionality of the login screen.
 * It handles user authentication, navigation to the homepage, and displaying error alerts.
 */
public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink registerLink;
    private SqliteAccountDAO accountDAO;

    // private static final String SESSION_USERNAME_KEY = "loggedInUsername";


    /**
     * Initializes the controller. Sets up event handlers for login, register, and enter key press.
     */
    @FXML
    public void initialize(){
        this.accountDAO = new SqliteAccountDAO();
        loginButton.setOnAction(event -> onLoginButtonClick());
        registerLink.setOnAction(event -> onRegisterLinkClick());
        usernameField.setOnAction(event -> onEnterKeyPressed());
        passwordField.setOnAction(event -> onEnterKeyPressed());
    }

    /**
     * Handles enter key press events.
     */
    @FXML
    protected void onEnterKeyPressed(){
        onLoginButtonClick();
    }


    /**
     * Handles login button click events.
     */
    @FXML
    protected void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean isLoggedIn = authenticateUser(username, password);
        SqliteAccountDAO accountDAO = new SqliteAccountDAO();
        Account account = accountDAO.getAccount(username); // Retrieve the account from the database

        if (!existedAccount(username, accountDAO.getAllAccounts())) {
            showErrorAlert("Can't find this account");
            return;
        }
        if (isLoggedIn) {
            // Store the logged-in user's username in the session
            // Preferences.userRoot().put(SESSION_USERNAME_KEY, username);
            Session.getInstance().setLoggedInAccount(account);
            System.out.println("session:" + username);


            navigationToHomepage();
        } else {
            showErrorAlert("Incorrect username or password.");
        }
    }

    /**
     * Checks if the account with the given username exists.
     * @param username The username to check.
     * @param accounts The list of accounts to search in.
     * @return True if the account exists, otherwise false.
     */
    protected boolean existedAccount(String username, List<Account> accounts) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Navigates to the homepage upon successful login.
     */
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

    /**
     * Handles register link click events.
     */
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
     * Authenticates the user with the provided username and password.
     * @param username The username of the user.
     * @param password The password of the user.
     * @return True if the authentication is successful, otherwise false.
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
     * Displays an error alert with the given message.
     * @param message The error message to display.
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert (Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}