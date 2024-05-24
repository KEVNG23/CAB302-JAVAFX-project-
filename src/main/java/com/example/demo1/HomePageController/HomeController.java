package com.example.demo1.HomePageController;

import com.example.demo1.AccountModel.Session;
import com.example.demo1.Main;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;

import java.io.IOException;

import java.util.List;

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


    private SqliteTaskDAO task;


    // Existing initialize() method

    public void initialize(){
        this.task = new SqliteTaskDAO();

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

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void handleTimerButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo1/timer-view.fxml"));
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

