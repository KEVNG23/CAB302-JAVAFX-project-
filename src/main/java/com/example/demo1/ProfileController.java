package com.example.demo1;

import com.example.demo1.AccountModel.Account;
import com.example.demo1.AccountModel.SqliteAccountDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.prefs.Preferences;

/**
 * The ProfileController class handles user profile-related functionality in a JavaFX application.
 */
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

    /**
     * Gets the SESSION_USERNAME_KEY constant.
     *
     * @return the SESSION_USERNAME_KEY constant
     */
    public static String getSessionUsernameKey() {
        return SESSION_USERNAME_KEY;
    }

    /**
     * Constructs a new ProfileController and initializes the SqliteAccountDAO.
     */
    public ProfileController() {
        this.accountDAO = new SqliteAccountDAO();
    }

    /**
     * Initializes the controller by retrieving the current user and updating the UI fields.
     */
    @FXML
    public void initialize() {
        currentUser = retrieveCurrentUser();
        updateUIFields();
    }

    /**
     * Retrieves the currently logged-in user.
     *
     * @return the currently logged-in user, or null if no user is logged in
     */
    private Account retrieveCurrentUser() {
        String loggedInUsername = getLoggedInUsername();
        if (loggedInUsername != null) {
            return accountDAO.getAccount(loggedInUsername);
        }
        return null;
    }

    /**
     * Retrieves the logged-in username from the session.
     *
     * @return the logged-in username, or null if no user is logged in
     */
    private String getLoggedInUsername() {
        return Preferences.userRoot().get(SESSION_USERNAME_KEY, null);
    }

    /**
     * Updates the UI fields with the current user's information.
     */
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

    /**
     * Event handler for re-affirming password change.
     * Displays the new password in the message area.
     */
    @FXML
    protected void onChangePassword() {
        String newPassword = passwordField.getText();
        messageArea.setText("New Password: " + newPassword);
    }

    /**
     * Event handler for re-affirming email change.
     * Displays the new email in the message area.
     */
    @FXML
    protected void onChangeEmail() {
        String newEmail = emailField.getText();
        messageArea.setText("New Email: " + newEmail);
    }

    /**
     * Event handler for saving the changes made to the user's profile.
     * Retrieves the current user, updates the user's password and email,
     * and saves the changes to the database.
     * Displays a success message in the message area.
     */
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