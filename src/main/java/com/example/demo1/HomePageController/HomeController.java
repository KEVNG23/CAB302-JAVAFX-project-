package com.example.demo1.HomePageController;

/**
 * Import statements.
 */
import com.example.demo1.AccountModel.Session;
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
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.List;

/**
 * The HomeController class manages the functionality of the home page.
 * It handles task management, navigation to other views, and user interaction with the UI elements.
 */
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
    private Text usernameText;

    private SqliteTaskDAO task;

    /**
     * Initializes the controller. Sets up the task table columns, displays the username,
     * and loads tasks for the logged-in user.
     */
    public void initialize(){
        this.task = new SqliteTaskDAO();

        taskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeFrame"));
        urgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgency"));

        Session session = Session.getInstance();
        if (session.getLoggedInAccount() != null) {
            String username = session.getLoggedInAccount().getUsername();
            usernameText.setText("Hello, " + username + "!");

            int accountId = session.getLoggedInAccount().getId();
            List<Task> userTasks = task.getAllTasks(accountId);
            taskTable.getItems().setAll(userTasks);
        } else {
            usernameText.setText("Hello!");
        }

    }

    /**
     * Refreshes the tasks displayed in the table for the logged-in user.
     */
    public void refreshTasks() {
        int accountId = Session.getInstance().getLoggedInAccount().getId();
        List<Task> userTasks = task.getAllTasks(accountId);
        taskTable.getItems().setAll(userTasks);
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Handles the event when the timer button is clicked. Navigates to the timer view.
     * @param event The action event triggered by clicking the timer button.
     * @throws IOException if the timer view FXML file cannot be loaded.
     */
    @FXML
    public void handleTimerButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo1/timer-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the event when the calendar button is clicked. Navigates to the calendar view.
     * @param event The action event triggered by clicking the calendar button.
     * @throws IOException if the calendar view FXML file cannot be loaded.
     */
    @FXML
    public void handleCalendarButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo1/calendar-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the event when the profile button is clicked. Navigates to the profile view.
     * @param event The action event triggered by clicking the profile button.
     * @throws IOException if the profile view FXML file cannot be loaded.
     */
    @FXML
    public void handleProfileButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo1/profile-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the event when the logout button is clicked. Navigates to the login view.
     * @param event The action event triggered by clicking the logout button.
     * @throws IOException if the login view FXML file cannot be loaded.
     */
    @FXML
    public void handleLogoutButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/demo1/login-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the event when the add task button is clicked. Adds a new task to the task list.
     */
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

    /**
     * Validates the inputs for adding a new task.
     * @param description The description of the task.
     * @param time The time frame for the task.
     * @param urgency The urgency level of the task.
     * @return True if all inputs are valid, otherwise false.
     */
    private boolean validateTaskInputs(String description, String time, String urgency) {
        return !(description.isEmpty() || time == null || urgency == null);
    }

    /**
     * Adds a new task to the task list and updates the UI.
     * @param task The task to add.
     * @return True if the task was successfully added, otherwise false.
     */
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

    /**
     * Clears the task input fields and resets the UI.
     */
    private void clearAndResetUI() {
        taskField.clear();
        timeFrameComboBox.setValue(null);
        urgencyComboBox.setValue(null);
    }

    /**
     * Handles the event when the remove task button is clicked. Removes the selected task from the task list.
     */
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

    /**
     * Removes a task from the task list and updates the UI.
     * @param task The task to remove.
     * @return True if the task was successfully removed, otherwise false.
     */
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

    /**
     * Displays an alert with the given title, message, and alert type.
     * @param title The title of the alert.
     * @param message The message to display in the alert.
     * @param type The type of the alert.
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation dialog with the given title and message.
     * @param title The title of the confirmation dialog.
     * @param message The message to display in the confirmation dialog.
     * @return True if the user confirmed the action, otherwise false.
     */
    private boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }
}

