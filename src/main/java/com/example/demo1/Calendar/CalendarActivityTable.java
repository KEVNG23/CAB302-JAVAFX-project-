package com.example.demo1.Calendar;

import com.example.demo1.AccountModel.Session;
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
    @FXML
    private TableColumn<CalendarActivity, Integer> account_idColumm;

    /**
     * DAO object for interacting with the calendar activities database.
     */
    private SqliteCalendarDAO calendarDAO;
    private CalendarController calendarController;

    /**
     * Setter method for setting the calendarDAO object.
     *
     * @param newCalendarDAO The calendarDAO object to be set.
     */
    public void setCalendarDAO(SqliteCalendarDAO newCalendarDAO) {
        this.calendarDAO = newCalendarDAO;
    }


    /**
     * Initializes the TableView and sets cell value factories for each column.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources specific to this controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Retrieve activities from the database
        Session session = Session.getInstance();
        int accountId = session.getLoggedInAccount().getId();
        List<CalendarActivity> activities = calendarDAO.getAllActivity(accountId);

        // Set cell value factories for each column
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        account_idColumm.setCellValueFactory(new PropertyValueFactory<>("account_id"));

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

    public void setCalendarController(CalendarController calendarController) {
        this.calendarController = calendarController;
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
            if (calendarController != null){
                calendarController.refreshCalendar();   //refresh Calendar in the main calendar view
            }
        }
    }
}
