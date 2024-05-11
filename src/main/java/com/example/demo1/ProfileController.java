package com.example.demo1;

import com.example.demo1.AccountModel.Account;
import com.example.demo1.AccountModel.SqliteAccountDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.prefs.Preferences;

public class ProfileController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField emailField;
    @FXML
    public TextArea messageArea;

    private SqliteAccountDAO accountDAO;
    private Account currentUser;

    private static final String SESSION_USERNAME_KEY = "loggedInUsername";
    public static String getSessionUsernameKey() {
        return SESSION_USERNAME_KEY;
    }

    public ProfileController() {
        this.accountDAO = new SqliteAccountDAO();
    }

    @FXML
    public void initialize() {
        currentUser = retrieveCurrentUser();
        updateUIFields();
    }

    private Account retrieveCurrentUser() {
        String loggedInUsername = getLoggedInUsername();
        if (loggedInUsername != null) {
            return accountDAO.getAccount(loggedInUsername);
        }
        return null;
    }

    private String getLoggedInUsername() {
        return Preferences.userRoot().get(SESSION_USERNAME_KEY, null);
    }

    private void updateUIFields() {
        if (currentUser != null) {
            usernameField.setText(currentUser.getUsername());
            emailField.setText(currentUser.getEmail());
        } else {
            usernameField.clear();
            passwordField.clear();
            emailField.clear();
        }
    }


    @FXML
    protected void onChangePassword() {
        String newPassword = passwordField.getText();
        messageArea.setText("New Password: " + newPassword);
    }

    @FXML
    protected void onChangeEmail() {
        String newEmail = emailField.getText();
        messageArea.setText("New Email: " + newEmail);
    }

    @FXML
    public void onSaveChanges() {
        // Retrieve the currently logged-in user's information from the session
        currentUser = retrieveCurrentUser();

        if (currentUser == null) {
            messageArea.setText("Please log in.");
            return;
        }

        String newPassword = passwordField.getText();
        String newEmail = emailField.getText();

        currentUser.setPassword(newPassword);
        currentUser.setEmail(newEmail);

        accountDAO.updateAccount(currentUser);
        messageArea.setText("Changes saved successfully.");
    }
}