package com.example.demo1.Timer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;


public class TimerController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void handleDashboardButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo1/home-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleCalendarButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo1/calendar-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleProfileButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo1/profile-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleLogoutButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo1/login-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToStopwatchPage() {
        try {
            // Load the timer view from the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/stopwatch-view.fxml"));
            Parent root = fxmlLoader.load();  // Load the root element from the FXML

            // Create a new stage for the timer window
            Stage stage = new Stage();
            stage.setTitle("Timer");  // Set a title for the window
            stage.setScene(new Scene(root));  // Set the scene to the new stage

            // Show the new stage, making the timer window visible
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load timer view", e);
        }
    }

    @FXML
    private TextField hourField;
    @FXML
    private TextField minuteField;

    @FXML
    private void addHour() {
        String hourText = hourField.getText();
        try {
            int hours = Integer.parseInt(hourText);
            hours++; // Increment the hours by 1
            hourField.setText(Integer.toString(hours)); // Set the updated value back to hourField
        } catch (NumberFormatException e) {
            // Handle the case where the input cannot be parsed as an integer
            System.err.println("Invalid input for hours: " + hourText);
        }
    }
    @FXML
    private void removeHour() {
        String hourText = hourField.getText();
        try {
            int hours = Integer.parseInt(hourText);
            if (hours > 0) { // Check if hours is greater than 0
                hours--; // Decrement the hours by 1
                hourField.setText(Integer.toString(hours)); // Set the updated value back to hourField
            }
        } catch (NumberFormatException e) {
            // Handle the case where the input cannot be parsed as an integer
            System.err.println("Invalid input for hours: " + hourText);
        }
    }
    @FXML
    private void addMinute() {
        String minuteText = minuteField.getText();
        try {
            int minutes = Integer.parseInt(minuteText);
            if (minutes < 59) {
                minutes++;
                minuteField.setText(Integer.toString(minutes));
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid input for minutes: " + minuteText);
        }
    }
    @FXML
    private void removeMinute() {
        String minuteText = minuteField.getText();
        try {
            int minutes = Integer.parseInt(minuteText);
            if (minutes > 0) {
                minutes--;
                minuteField.setText(Integer.toString(minutes));
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid input for minutes: " + minuteText);
        }
    }

    @FXML
    protected void onStartButtonClick() {
        String hourInput = hourField.getText();
        String minuteInput = minuteField.getText();

        int hours = parseTimeInput(hourInput);
        int minutes = parseTimeInput(minuteInput);

        int seconds = hours * 3600 + minutes * 60;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/countdown-view.fxml"));
            Parent root = loader.load();

            CountdownViewController controller = loader.getController();
            controller.setCountdownTime(seconds);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private int parseTimeInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // Handle invalid input
            return 0;
        }
    }
}
