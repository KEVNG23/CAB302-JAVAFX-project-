package com.example.demo1.AccountController;

import com.example.demo1.AccountModel.Account;
import com.example.demo1.AccountModel.SqliteAccountDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.demo1.Main;

import java.io.IOException;
import java.util.List;


/**
 * The RegisterController class manages the functionality of the user registration screen.
 * It handles user registration, password validation, navigation to the login screen, and displaying error alerts.
 */
public class RegisterController {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Hyperlink loginLink;

    @FXML
    private Button registerButton;

    private SqliteAccountDAO account;

    /**
     * Initializes the controller. Sets up event handlers for register, login link, and enter key press.
     */
    @FXML
    public void initialize(){
        account = new SqliteAccountDAO();
        registerButton.setOnAction(event -> onRegisterButtonClick());
        loginLink.setOnAction(event -> onLoginLinkClick());
        usernameField.setOnAction(event -> onEnterKeyPressed());
        emailField.setOnAction(event -> onEnterKeyPressed());
        passwordField.setOnAction(event -> onEnterKeyPressed());
        confirmPasswordField.setOnAction(event -> onEnterKeyPressed());
    }

    /**
     * Handles enter key press events.
     */
    @FXML
    protected void onEnterKeyPressed(){
        onRegisterButtonClick();
    }

    /**
     * Navigates to the login screen.
     */
    @FXML
    protected void navigationToLogin() {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), Main.WIDTH, Main.HEIGHT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
    }

    /**
     * Handles register button click events.
     */
    @FXML
    protected void onRegisterButtonClick() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        boolean isPasswordEqual = confirmedPasswordCheck(password, confirmPassword);
        if(username.isEmpty() | email.isEmpty() | password.isEmpty() | confirmPassword.isEmpty()){
            showErrorAlert("Please fill in all fields");
            return;
        }
        if(!isPasswordEqual) {
            showErrorAlert("Password do not match");
            return;

        }
        if (!Account.isValidEmail(email)) {
            showErrorAlert("Invalid email address. The email must be from a valid domain.");
            return;
        }
        SqliteAccountDAO accountDAO = new SqliteAccountDAO();
        if (existedAccount(username, accountDAO.getAllAccounts())){
            showErrorAlert("Account already existed");
            return;
        }
        Account newAccount = new Account(null, username, email, password);
        accountDAO.addAccount(newAccount);

        navigationToLogin();
    }

    /**
     * Checks if the password and confirm password fields match.
     * @param password The password entered by the user.
     * @param confirmPassword The confirmed password entered by the user.
     * @return True if passwords match, otherwise false.
     */
    protected boolean confirmedPasswordCheck(String password, String confirmPassword) {
        return confirmPassword.equals(password);
    }

    /**
     * Checks if an account with the given username already exists.
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
     * Handles login link click events.
     */
    @FXML
    protected void onLoginLinkClick() {
        Stage stage = (Stage) loginLink.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), Main.WIDTH, Main.HEIGHT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
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

