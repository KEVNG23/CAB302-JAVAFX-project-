package com.example.demo1.Controller;

import com.example.demo1.Models.Account;
import com.example.demo1.Models.SqliteAccountDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.demo1.Main;

import java.io.IOException;
import java.util.List;

public class RegisterController {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField confirmPasswordField;

    @FXML
    private Hyperlink loginLink;

    @FXML
    private Button registerButton;

    private SqliteAccountDAO account;

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

    @FXML
    protected void onEnterKeyPressed(){
        onRegisterButtonClick();
    }

    @FXML
    protected void navigationToHomepage() {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-view.fxml")); // replace with actual homepage
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
    }


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
        SqliteAccountDAO accountDAO = new SqliteAccountDAO();
        if (existedAccount(username, accountDAO.getAllAccounts())){
            showErrorAlert("Account already existed");
            return;
        }
        Account newAccount = new Account(null, username, email, password);
        accountDAO.addAccount(newAccount);

        navigationToHomepage();
    }

    /**
     * boolean method check the user is entered the same password
     * @param password get String value from password
     * @param confirmPassword get String value from confirm password
     * @return true if the password and confirmPassword is equal, else return false
     */
    protected boolean confirmedPasswordCheck(String password, String confirmPassword) {
        return confirmPassword.equals(password);
    }

    /**
     *
     * @param username
     * @param accounts
     * @return
     */
    protected boolean existedAccount(String username, List<Account> accounts) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }



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

    private void showErrorAlert(String message) {
        Alert alert = new Alert (Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

