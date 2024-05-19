package com.example.demo1.Calendar;

import com.example.demo1.AccountModel.Session;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Controller class for the activity dialog view.
 */
public class ActivityDialogController {

    /**
     * JavaFX TextField for entering client name.
     */
    @FXML
    private TextField clientNameField;

    /**
     * JavaFX DatePicker for selecting activity date.
     */
    @FXML
    private DatePicker datePicker;

    /**
     * JavaFX RadioButton for setting activity as important.
     */
    @FXML
    private RadioButton importantRadioButton;

    /**
     * JavaFX RadioButton for setting activity as average priority.
     */
    @FXML
    private RadioButton averageRadioButton;

    /**
     * JavaFX RadioButton for setting activity as not important.
     */
    @FXML
    private RadioButton notImportantRadioButton;

    /**
     * Reference to the parent CalendarController.
     */
    private CalendarController calendarController;


    /**
     * Sets the calendar controller associated with this dialog controller.
     *
     * @param calendarController The parent CalendarController instance.
     */
    public void setCalendarController(CalendarController calendarController) {
        this.calendarController = calendarController;
    }


    /**
     * Handles the action of saving a new activity.
     * Retrieves input values, validates them, creates a new CalendarActivity,
     * and adds it to the CalendarController.
     */
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
        CalendarActivity newCalendarActivity = new CalendarActivity(null, title, date, priority);

        Session session = Session.getInstance();
        // Pass the new activity to the calendarController
        if (calendarController != null) {
            calendarController.addActivity(newCalendarActivity, session);
            calendarController.drawCalendar(); // Redraw the calendar
        }

        // Close the pop-up window
        Stage stage = (Stage) clientNameField.getScene().getWindow();
        stage.close();


    }
}
