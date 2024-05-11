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

    // getter method for the SESSION_USERNAME_KEY constant
    // use this to allow password and email change only for the user that is logged in
    public static String getSessionUsernameKey() {
        return SESSION_USERNAME_KEY;
    }

    // constructor initializes the SqliteAccountDAO
    public ProfileController() {
        this.accountDAO = new SqliteAccountDAO();
    }

    // initialization method
    @FXML
    public void initialize() {
        currentUser = retrieveCurrentUser();
        updateUIFields();
    }
    // retrieves the currently logged-in user
    private Account retrieveCurrentUser() {
        String loggedInUsername = getLoggedInUsername();
        if (loggedInUsername != null) {
            return accountDAO.getAccount(loggedInUsername);
        }
        return null;
    }

    // retrieves the logged-in username
    private String getLoggedInUsername() {
        return Preferences.userRoot().get(SESSION_USERNAME_KEY, null);
    }

    // updates the ui fields with the current user's information
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

    // event handler for re-affirming password change
    @FXML
    protected void onChangePassword() {
        String newPassword = passwordField.getText();
        messageArea.setText("New Password: " + newPassword);
    }

    // event handler for re-affirming email change
    @FXML
    protected void onChangeEmail() {
        String newEmail = emailField.getText();
        messageArea.setText("New Email: " + newEmail);
    }

    // event handler for saving the changes
    @FXML
    public void onSaveChanges() {
        // retrieve the currently logged-in user's information from the session
        currentUser = retrieveCurrentUser();

        if (currentUser == null) {
            messageArea.setText("Please log in.");
            return;
        }

        String newPassword = passwordField.getText();
        String newEmail = emailField.getText();

        // update the current user's password and email
        currentUser.setPassword(newPassword);
        currentUser.setEmail(newEmail);

        // update the user's information in the database
        accountDAO.updateAccount(currentUser);
        messageArea.setText("Changes saved successfully.");
    }
}