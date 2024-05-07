package com.example.demo1.Timer;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class StopwatchController {
    @FXML
    public TextField stopwatchLabel;

    private long startTime = 0;
    private long pausedTime = 0;
    private AnimationTimer timer;
    private boolean running = false;

    @FXML
    public void onStopStartClick() {
        change();
        if (running) {
            pauseTimer();
        } else {
            startTimer();
        }
    }

    private void startTimer() {
        if (!running) {
            if (pausedTime == 0) {
                startTime = System.currentTimeMillis();
            } else {
                startTime = System.currentTimeMillis() - pausedTime; // Resume from paused time
                pausedTime = 0; // Reset paused time
            }
            running = true;
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    updateTimerLabel();
                }
            };
            timer.start();
        }
    }

    private void pauseTimer() {
        if (running) {
            pausedTime = System.currentTimeMillis() - startTime;
            running = false;
            timer.stop();
        }
    }

    private void updateTimerLabel() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long totalElapsedTime = elapsedTime + pausedTime; // Consider paused time in total elapsed time
        long milliseconds = totalElapsedTime % 1000;
        long seconds = totalElapsedTime / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        stopwatchLabel.setText(String.format("%02d:%02d.%02d", minutes, seconds, milliseconds / 10));

        if (stopwatchLabel.getText().equals("01:00.00")) {
            showHourlyAlert();
        }
    }


    @FXML
    private void onResetClick() {
        resetTimer();
        pauseTimer();
    }

    private void resetTimer() {
        pausedTime = 0;
        stopwatchLabel.setText("00:00.00");
        if (running) {
            startTime = System.currentTimeMillis(); // Restart the timer from the current time
            running = true; // Set running flag to true
            timer.start(); // Resume the timer
            btnStart.setText("START"); // Update button text
        } else {
            btnStart.setText("START"); // Reset button text if not running
        }
    }

    @FXML
    private ListView flagList;

    @FXML
    private void onFlagClick() {
        if (running) {
            String flagTime = stopwatchLabel.getText();
            flagList.getItems().addAll(flagTime);
        }
    }

    @FXML
    private Button btnStart;

    private void change() {
        String btnText = btnStart.getText();
        if (btnText.equals("START")) {
            btnStart.setText("PAUSE");
        } else if (btnText.equals("PAUSE")) {
            btnStart.setText("START");
        }
    }

    private void showHourlyAlert() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Hourly Update");
            alert.setHeaderText(null);
            alert.setContentText("The timer has been running for an hour! Maybe you should take a break.");

            alert.showAndWait();
        });
    }
}
