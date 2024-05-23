package com.example.demo1.Profile;

import com.example.demo1.AccountModel.Account;
import com.example.demo1.AccountModel.Session;
import com.example.demo1.AccountModel.SqliteAccountDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.demo1.HomePageController.HomeController;
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
    private HomeController homeController;

    /**
     * Constructs a new ProfileController and initializes the SqliteAccountDAO.
     */
    public ProfileController() {
        this.accountDAO = new SqliteAccountDAO();
    }

    /**
     * Initializes the controller by retrieving the current user from the session and updating the UI fields.
     */
    @FXML
    public void initialize() {
        currentUser = Session.getInstance().getLoggedInAccount();
        updateUIFields();
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
     * Checks if email or password field is blank before admitting changes to the database
     */
    @FXML
    public void onSaveChanges() {
        if (currentUser == null) {
            messageArea.setText("Please log in.");
            return;
        }

        String newPassword = passwordField.getText();
        String newEmail = emailField.getText();

        if (!newPassword.isBlank()) {
            currentUser.setPassword(newPassword);
        }

        if (!newEmail.isBlank()) {
            currentUser.setEmail(newEmail);
        }

        accountDAO.updateAccount(currentUser);
        messageArea.setText("Changes saved successfully.");
    }
    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    @FXML
    protected void handleTimerButtonClick() {}
    @FXML
    protected void handleCalendarButtonClick() {}
}
