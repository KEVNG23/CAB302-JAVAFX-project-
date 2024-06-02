package com.example.demo1.Timer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CountdownViewController {
    @FXML
    private Label countdownLabel;
    private int secondsRemaining;
    private boolean isCountdownRunning;
    private boolean isPaused;


    public void setCountdownTime(int seconds) {
        this.secondsRemaining = seconds;
        this.isCountdownRunning = true;
        this.isPaused = false;
        startCountdown();
    }

    private void startCountdown() {
        new Thread(() -> {
            try {
                while (secondsRemaining > 0 && isCountdownRunning) {
                    if (!isPaused) {
                        Thread.sleep(1000);
                        secondsRemaining--;

                        updateCountdownLabel();

                        if (secondsRemaining % 3600 == 0) {
                            showHourlyAlert();
                        }
                    } else {
                        Thread.sleep(100);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle interruption if needed
            } finally {
                // Reset the flag when countdown is stopped
                isCountdownRunning = false;
                isPaused = false;
            }
        }).start();
    }

    private void updateCountdownLabel() {
        int hours = secondsRemaining / 3600;
        int minutes = (secondsRemaining % 3600) / 60;
        int remainingSeconds = secondsRemaining % 60;

        String timeString = String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);

        // Update UI on JavaFX application thread
        Platform.runLater(() -> countdownLabel.setText(timeString));
    }

    @FXML
    private void onCancelButtonClick() {
        isCountdownRunning = false;

        Stage stage = (Stage) countdownLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onPauseButtonClick() {
        isPaused = !isPaused;
    }

    private void showHourlyAlert() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Timer Update");
            alert.setHeaderText(null);
            alert.setContentText("The timer has finished! Maybe you should take a break.");

            alert.showAndWait();
        });
    }


}