package com.example.demo1.Controller;

import com.example.demo1.Models.CalendarActivity;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ActivityDialogController {

    @FXML
    private TextField clientNameField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private RadioButton importantRadioButton;

    @FXML
    private RadioButton averageRadioButton;

    @FXML
    private RadioButton notImportantRadioButton;


    private CalendarController calendarController;

    public void setCalendarController(CalendarController calendarController) {
        this.calendarController = calendarController;
    }

    @FXML
    void saveActivity() {
        String title = clientNameField.getText();
        LocalDate date = datePicker.getValue();
        String priority = ""; // Initialize priority

        // Check which priority radio button is selected
        if (importantRadioButton.isSelected()) {
            priority = "URG";
        } else if (averageRadioButton.isSelected()) {
            priority = "AVE";
        } else if (notImportantRadioButton.isSelected()) {
            priority = "Not URG";
        }

        // Validate inputs
        if (title.isEmpty() || date == null || priority.isEmpty()) {
            // Show an error message or handle invalid input
            return;
        }

        // Create the activity
        CalendarActivity newCalendarActivity = new CalendarActivity(title, date, priority);

        // Pass the new activity to the calendarController
        if (calendarController != null) {
            calendarController.addActivity(newCalendarActivity);
            calendarController.drawCalendar(); // Redraw the calendar
        }

        // Close the pop-up window
        Stage stage = (Stage) clientNameField.getScene().getWindow();
        stage.close();


    }
}
