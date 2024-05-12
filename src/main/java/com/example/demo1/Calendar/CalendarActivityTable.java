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

public class CalendarActivityTable implements Initializable {

    @FXML
    private TableView<CalendarActivity> activityTable;

    @FXML
    private TableColumn<CalendarActivity, Integer> idColumn;

    @FXML
    private TableColumn<CalendarActivity, String> titleColumn;

    @FXML
    private TableColumn<CalendarActivity, LocalDate> dateColumn;

    @FXML
    private TableColumn<CalendarActivity, String> priorityColumn;

    private final ICalendarDAO calendarDAO = new SqliteCalendarDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set cell value factories for each column
     //   idColumn.setCellValueFactory(((CalendarActivity)calendarDAO.getAllActivity().toArray()[0]).getId());
        idColumn.setCellValueFactory(new PropertyValueFactory<CalendarActivity,Integer>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<CalendarActivity,String>("title"));
      //  priorityColumn.setCellValueFactory(cellData -> cellData.getValue().priorityProperty());
        priorityColumn.setCellValueFactory(new PropertyValueFactory<CalendarActivity,String>("priority"));
    }

    public void setActivities(List<CalendarActivity> activities) {
        activityTable.getItems().setAll(activities);
    }

    @FXML
    void removeActivity() {
        CalendarActivity selectedActivity = activityTable.getSelectionModel().getSelectedItem();
        if (selectedActivity != null) {
            calendarDAO.deleteActivity(selectedActivity);
            activityTable.getItems().remove(selectedActivity);
        }
    }
}
