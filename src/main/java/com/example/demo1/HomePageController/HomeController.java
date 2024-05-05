package com.example.demo1.HomePageController;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class HomeController {
    @FXML
    private TableView<TaskDetails> taskTable;

    @FXML
    private TableColumn<TaskDetails, String> taskColumn;

    @FXML
    private TableColumn<TaskDetails, String> timeColumn;

    @FXML
    private TableColumn<TaskDetails, String> urgencyColumn;

    @FXML
    private TextField taskField;

    @FXML
    private ComboBox<String> timeFrameComboBox;

    @FXML
    private ComboBox<String> urgencyComboBox;

    @FXML
    private Text usernameText; // Add Text element for displaying username

    private DatabaseHandler databaseHandler;

    // Existing initialize() method
    @FXML
    private void initialize() {
        // Initialize DatabaseHandler
        databaseHandler = new DatabaseHandler();
        try {
            // Establish database connection
            databaseHandler.connect();

            // Retrieve username for the logged-in user from the database
            String username = databaseHandler.getUsernameForLoggedInUser();
            if (username != null) {
                usernameText.setText("Hello, " + username + "!");
            } else {
                // Handle case when username is not found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle connection error or database error
        }

        // Existing initialization code
        taskColumn.setCellValueFactory(cellData -> {
            String value = cellData.getValue().getTask();
            return new SimpleStringProperty(value);
        });

        timeColumn.setCellValueFactory(cellData -> {
            String value = cellData.getValue().getTimeFrame();
            return new SimpleStringProperty(value);
        });

        urgencyColumn.setCellValueFactory(cellData -> {
            String value = cellData.getValue().getUrgency();
            return new SimpleStringProperty(value);
        });
    }
    private static class TaskDetails {
        private final String task;
        private final String timeFrame;
        private final String urgency;

        public TaskDetails(String task, String timeFrame, String urgency) {
            this.task = task;
            this.timeFrame = timeFrame;
            this.urgency = urgency;
        }

        // Getters for task details
        public String getTask() {
            return task;
        }

        public String getTimeFrame() {
            return timeFrame;
        }

        public String getUrgency() {
            return urgency;
        }
    }
    @FXML
    private void handleAddTask() {
        String newTask = taskField.getText().trim();
        String timeFrame = timeFrameComboBox.getValue(); // Get selected time frame
        String urgency = urgencyComboBox.getValue(); // Get selected urgency

        if (!newTask.isEmpty() && timeFrame != null && urgency != null) {
            TaskDetails taskDetails = new TaskDetails(newTask, timeFrame, urgency);
            taskTable.getItems().add(taskDetails);
            taskField.clear();
            timeFrameComboBox.setValue(null); // Reset ComboBox value after adding task
            urgencyComboBox.setValue(null); // Reset ComboBox value after adding task
        }
    }

    @FXML
    private void handleRemoveTask() {
        int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            taskTable.getItems().remove(selectedIndex);
        }
    }
    @FXML
    private void handleCalendarButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/calendar-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Calendar");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading calendar-view.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void handleTimerButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/timer-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Timer");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading timer-view.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void handleProfileButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/hello-view1.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Profile");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading hello-view1.fxml: " + e.getMessage());
        }
    }

    // Add other event handler methods for other buttons if needed
}
