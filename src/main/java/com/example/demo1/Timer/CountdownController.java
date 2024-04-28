package com.example.demo1.Timer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CountdownController{
    @FXML
    private TextField durationField;
    @FXML
    private Label countdownLabel;

    @FXML
    protected void onStartButtonClick() {
        String input = durationField.getText();
        int seconds = parseTimeInput(input);

        new Thread(() -> startCountdown(seconds, countdownLabel)).start();
    }

    private int parseTimeInput(String input) {
        String[] parts = input.split(":");
        if (parts.length != 2) {
            // Handle invalid input
            return 0;
        }

        try {
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            return hours * 3600 + minutes * 60;
        } catch (NumberFormatException e) {
            // Handle invalid input
            return 0;
        }
    }

    @FXML
    private void startCountdown(int seconds, Label countdownLabel) {
        try {
            while (seconds > 0) {
                Thread.sleep(1000);
                seconds--;

                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int remainingSeconds = seconds % 60;

                String timeString = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);

                // Update UI on JavaFX application thread
                javafx.application.Platform.runLater(() -> countdownLabel.setText(timeString));
            }
        } catch (InterruptedException e) {
            // Handle interruption if needed
        }
    }
}