package com.example.demo1;

import com.example.demo1.AccountModel.Account;
import com.example.demo1.AccountModel.SqliteAccountDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProfileController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextArea messageArea;

    private SqliteAccountDAO accountDAO;

    public ProfileController() {
        this.accountDAO = new SqliteAccountDAO();
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
    protected void onSaveChanges() {
        String newUsername = usernameField.getText();
        String newPassword = passwordField.getText();
        String newEmail = emailField.getText();

        Account account = accountDAO.getAccount(newUsername);
        if (account == null) {
            messageArea.setText("User does not exist.");
            return;
        }

        account.setUsername(newUsername);
        account.setPassword(newPassword);
        account.setEmail(newEmail);

        accountDAO.updateAccount(account);
        messageArea.setText("Changes saved successfully.");
    }
}
