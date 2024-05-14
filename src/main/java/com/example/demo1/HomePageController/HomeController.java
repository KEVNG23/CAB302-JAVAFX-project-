package com.example.demo1.HomePageController;

import com.example.demo1.AccountModel.Session;
import com.example.demo1.AccountModel.SqliteAccountDAO;
import com.example.demo1.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController {
    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> taskColumn;

    @FXML
    private TableColumn<Task, String> timeColumn;

    @FXML
    private TableColumn<Task, String> urgencyColumn;

    @FXML
    private TextField taskField;

    @FXML
    private ComboBox<String> timeFrameComboBox;

    @FXML
    private ComboBox<String> urgencyComboBox;

    @FXML
    private Text usernameText; // Add Text element for displaying username

    @FXML
    private Button logoutButton;

    @FXML
    private Button timerButton;

    @FXML
    private Button calendarButton;

    @FXML
    private Button profileButton;

    private SqliteTaskDAO task;


    // Existing initialize() method

    public void initialize(){
        this.task = new SqliteTaskDAO();
        timerButton.setOnAction(event -> handleTimerButtonClick());
        calendarButton.setOnAction(event -> handleCalendarButtonClick());
        profileButton.setOnAction(event -> handleProfileButtonClick());
        logoutButton.setOnAction(event -> handleLogoutButtonClick());

        taskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeFrame"));
        urgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgency"));

        // Set up username display
        Session session = Session.getInstance();
        if (session.getLoggedInAccount() != null) {
            String username = session.getLoggedInAccount().getUsername(); // Get the username from the session
            usernameText.setText("Hello, " + username + "!"); // Set the text with greeting

            int accountId = session.getLoggedInAccount().getId();
            List<Task> userTasks = task.getAllTasks(accountId);
            taskTable.getItems().setAll(userTasks);
        } else {
            usernameText.setText("Hello!");
        }

    }

    public void refreshTasks() {
        int accountId = Session.getInstance().getLoggedInAccount().getId();
        List<Task> userTasks = task.getAllTasks(accountId);
        taskTable.getItems().setAll(userTasks);
    }


    @FXML
    protected void handleTimerButtonClick() {
        try {
            // Load the timer view from the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/timer-view.fxml"));
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
    protected void handleCalendarButtonClick() {
        try {
            // Load the timer view from the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/calendar-view.fxml"));
            Parent root = fxmlLoader.load();  // Load the root element from the FXML

            // Create a new stage for the timer window
            Stage stage = new Stage();
            stage.setTitle("Calendar");  // Set a title for the window
            stage.setScene(new Scene(root));  // Set the scene to the new stage

            // Show the new stage, making the timer window visible
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load calendar view", e);
        }
    }

    @FXML
    protected void handleProfileButtonClick() {
        try {
            // Load the timer view from the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/profile-view.fxml"));
            Parent root = fxmlLoader.load();  // Load the root element from the FXML

            // Create a new stage for the timer window
            Stage stage = new Stage();
            stage.setTitle("Profile");  // Set a title for the window
            stage.setScene(new Scene(root));  // Set the scene to the new stage

            // Show the new stage, making the timer window visible
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load profile view", e);
        }
    }

    @FXML
    protected void handleLogoutButtonClick() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/login-view.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxmlLoader.load(), Main.WIDTH, Main.HEIGHT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
    }

    @FXML
    protected void handleAddTask() {
        String taskDescription = taskField.getText();
        String timeFrame = timeFrameComboBox.getValue();
        String urgency = urgencyComboBox.getValue();

        if (!validateTaskInputs(taskDescription, timeFrame, urgency)) {
            showAlert("Validation Error", "All fields must be filled!", Alert.AlertType.ERROR);
            return;
        }

        Task newTask = new Task(0, taskDescription, timeFrame, urgency); // Ensure the Task constructor matches this signature
        if (addNewTask(newTask)) {
            showAlert("Success", "Task added successfully.", Alert.AlertType.INFORMATION);
            clearAndResetUI();
            refreshTasks();
        } else {
            showAlert("Error", "Failed to add task.", Alert.AlertType.ERROR);
        }
    }

    private boolean validateTaskInputs(String description, String time, String urgency) {
        return !(description.isEmpty() || time == null || urgency == null);
    }

    private boolean addNewTask(Task task) {
        try {
            this.task.addTask(task, Session.getInstance());
            taskTable.getItems().add(task);
            return true;
        } catch (Exception e) {
            System.err.println("Error adding task: " + e.getMessage());
            return false;
        }
    }

    private void clearAndResetUI() {
        taskField.clear();
        timeFrameComboBox.setValue(null);
        urgencyComboBox.setValue(null);
    }

    @FXML
    protected void handleRemoveTask() {
        Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            showAlert("No Selection", "No Task Selected. Please select a task in the table.", Alert.AlertType.ERROR);
            return;
        }

        if (showConfirmation("Confirm Delete", "Are you sure you want to delete this task?")) {
            if (removeTask(selectedTask)) {
                showAlert("Success", "Task removed successfully.", Alert.AlertType.INFORMATION);
                refreshTasks();
            } else {
                showAlert("Error", "Failed to remove task.", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean removeTask(Task task) {
        try {
            this.task.removeTask(task, Session.getInstance());
            taskTable.getItems().remove(task);
            return true;
        } catch (Exception e) {
            System.err.println("Error removing task: " + e.getMessage());
            return false;
        }
    }
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }




}

