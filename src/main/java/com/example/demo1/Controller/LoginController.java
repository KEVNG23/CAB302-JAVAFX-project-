package com.example.demo1.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.demo1.Main;
import javafx.fxml.FXML;
import com.example.demo1.Models.Account;
import com.example.demo1.Models.MockAccount;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Hyperlink registerLink;
    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    public void initialize(){
        loginButton.setOnAction(event -> onLoginButtonClick());
        registerLink.setOnAction(event -> onRegisterLinkClick());
        forgotPasswordLink.setOnAction(event -> onForgotPasswordLinkClick());
    }



    @FXML
    protected void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean isLoggedIn = authenticateUser (username, password);
        if(isLoggedIn){
            navigationToHomepage();
        }else{
            showErrorAlert("Incorrect username or password.");
        }

    }



    @FXML
    protected void navigationToHomepage() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("###"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), Main.WIDTH, Main.HEIGHT);
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

    @FXML
    protected void onForgotPasswordLinkClick() {
    }


    private boolean authenticateUser(String username, String password) {
        //TODO: retrieve Account object from database
        Account account = MockAccount.generateMockAccount(username);

        if(account != null){
            return account.verifyPassword(password);
        }
        return false;
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert (Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}