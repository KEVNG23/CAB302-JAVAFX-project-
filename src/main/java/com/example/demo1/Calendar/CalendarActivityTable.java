package com.example.demo1.Calendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the activity table view.
 */
public class CalendarActivityTable implements Initializable {

    /**
     * JavaFX TableView for displaying calendar activities.
     */
    @FXML
    private TableView<CalendarActivity> activityTable;

    /**
     * JavaFX TableColumn for displaying activity IDs.
     */
    @FXML
    private TableColumn<CalendarActivity, Integer> idColumn;

    /**
     * JavaFX TableColumn for displaying activity titles.
     */
    @FXML
    private TableColumn<CalendarActivity, String> titleColumn;

    /**
     * JavaFX TableColumn for displaying activity dates.
     */
    @FXML
    private TableColumn<CalendarActivity, LocalDate> dateColumn;

    /**
     * JavaFX TableColumn for displaying activity priorities.
     */
    @FXML
    private TableColumn<CalendarActivity, String> priorityColumn;

    /**
     * DAO object for interacting with the calendar activities database.
     */
    private final ICalendarDAO calendarDAO = new SqliteCalendarDAO();


    /**
     * Initializes the TableView and sets cell value factories for each column.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources specific to this controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set cell value factories for each column
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));

        // Retrieve activities from the database
        List<CalendarActivity> activities = calendarDAO.getAllActivity();

        // Set the activities into the TableView
        setActivities(activities);
    }


    /**
     * Sets the list of calendar activities to be displayed in the table.
     *
     * @param activities The list of CalendarActivity objects.
     */
    public void setActivities(List<CalendarActivity> activities) {
        activityTable.getItems().setAll(activities);
    }


    /**
     * Handles the action of removing a selected activity from the table.
     * Deletes the activity from the database and updates the table view.
     */
    @FXML
    void removeActivity() {
        CalendarActivity selectedActivity = activityTable.getSelectionModel().getSelectedItem();
        if (selectedActivity != null) {
            calendarDAO.deleteActivity(selectedActivity);
            activityTable.getItems().remove(selectedActivity);
        }
    }
}
