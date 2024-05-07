package com.example.demo1.Timer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class TimerController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToStopwatchPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("stopwatch-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("countdown-view.fxml"));
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
