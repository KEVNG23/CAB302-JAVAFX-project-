package com.example.demo1.HomePageController;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
public class HomeController {

    @FXML
    private TableView<String> taskTable;

    @FXML
    private TableColumn<String, String> taskColumn;

    @FXML
    private TextField taskField;


    @FXML
    private void initialize() {
        // Set up the task column
        taskColumn.setCellValueFactory(cellData -> {
            String value = cellData.getValue(); // Get the value of the cell
            return new SimpleStringProperty(value); // Convert the value to an ObservableValue
        });
    }


    @FXML
    private void handleAddTask() {
        String newTask = taskField.getText().trim();
        if (!newTask.isEmpty()) {
            taskTable.getItems().add(newTask);
            taskField.clear();
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
        }
    }

    // Add other event handler methods for other buttons if needed
}
