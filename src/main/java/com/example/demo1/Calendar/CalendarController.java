package com.example.demo1.Calendar;

import com.example.demo1.AccountModel.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Controller class for the calendar view.
 */
public class CalendarController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    private SqliteCalendarDAO calendarDAO;
    private List<CalendarActivity> activities;


    /**public CalendarController(){
        this.calendarDAO = new SqliteCalendarDAO();
    }*/


    /**
     * Initializes the calendar with the current date and draws the calendar view.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources specific to this controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.calendarDAO = new SqliteCalendarDAO();
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        this.activities = new ArrayList<>();
        loadUserActivities();
        drawCalendar();
    }

    private void loadUserActivities() {
        Session session = Session.getInstance();
        if (session.getLoggedInAccount() != null) {
            int accountId = session.getLoggedInAccount().getId();
            this.activities = calendarDAO.getAllActivity(accountId);
        }
    }

    /**
     * Handles the action of navigating back one month in the calendar view.
     *
     * @param event The action event triggering the method call.
     */
    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }


    /**
     * Handles the action of navigating forward one month in the calendar view.
     *
     * @param event The action event triggering the method call.
     */
    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }


    /**
     * Opens a new activity dialog for adding a new activity to the calendar.
     *
     * @param event The action event triggering the method call.
     * @throws IOException If an error occurs while loading the dialog view.
     */
    @FXML
    void addNewActivity(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/activity-dialog.fxml"));
        Parent root = loader.load();

        // Get the controller of the activity dialog
        ActivityDialogController dialogController = loader.getController();

        // Set the calendar controller
        dialogController.setCalendarController(this);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.setTitle("Add New Activity");
        stage.showAndWait();
    }


    /**
     * Shows a table view of calendar activities.
     *
     * @param event The action event triggering the method call.
     */
    @FXML
    void showActivityTable(ActionEvent event) {
        Session session = Session.getInstance();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/CalendarActivityTable.fxml"));
            Parent root = loader.load();

            CalendarActivityTable tableController = loader.getController();
            tableController.setCalendarDAO(new SqliteCalendarDAO()); // Pass the calendarDAO object
            tableController.setCalendarController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Activity Table");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void refreshCalendar() {
        loadUserActivities(); // Reload activities from the database
        drawCalendar(); // Redraw the calendar
    }


    /**
     * Adds a new activity to the calendar and redraws the calendar view.
     *
     * @param calendarActivity The CalendarActivity to be added.
     */
    public void addActivity(CalendarActivity calendarActivity, Session session) {
        if (session.getLoggedInAccount() != null) {
            calendarDAO.addActivity(calendarActivity, session);
            activities.add(calendarActivity);
            drawCalendar();
        }
    }


    /**
     * Draws the calendar view based on the currently focused date.
     * Retrieves activities for each day and displays them on the calendar.
     */
    public void drawCalendar() {
        Session session = Session.getInstance();
        // Clear the existing calendar before drawing the new one
        calendar.getChildren().clear();

        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        // Fetch activities from the database
        int accountId = session.getLoggedInAccount().getId();
        activities = calendarDAO.getAllActivity(accountId);

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        int monthMaxDate = dateFocus.getMonth().maxLength();
        // Check for leap year
        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text dateText = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        dateText.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(dateText);

                        // Get activities for this date
                        LocalDate currentDateObj = LocalDate.of(dateFocus.getYear(), dateFocus.getMonthValue(), currentDate);
                        List<CalendarActivity> activitiesOnDate = getActivitiesOnDate(currentDateObj);

                        // Create a VBox to hold activity information
                        VBox activityInfoBox = new VBox();
                        activityInfoBox.setMaxWidth(rectangleWidth);
                        activityInfoBox.setMaxHeight(rectangleHeight);

                        // Add activity information to the VBox
                        for (CalendarActivity calendarActivity : activitiesOnDate) {
                            Text activityText = new Text(calendarActivity.getTitle());
                            Text priorityText = new Text("Priority: " + calendarActivity.getPriority()); // Add priority information
                            activityInfoBox.getChildren().addAll(activityText, priorityText);
                        }

                        // Set the position of the VBox
                        activityInfoBox.setTranslateX(5); // Adjust as needed
                        activityInfoBox.setTranslateY(5); // Adjust as needed

                        // Add the VBox to the stackPane
                        stackPane.getChildren().add(activityInfoBox);

                    }
                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.BLUE);
                    } else if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.RED); // Example: Highlight with red color
                    }
                }

                // Add stackPane to calendar.getChildren()
                calendar.getChildren().add(stackPane);
            }
        }
    }


    /**
     * Retrieves activities scheduled on a specific date.
     *
     * @param date The date for which activities are to be retrieved.
     * @return A list of CalendarActivity objects scheduled for the given date.
     */
    public List<CalendarActivity> getActivitiesOnDate(LocalDate date) {
        List<CalendarActivity> activitiesOnDate = new ArrayList<>();
        for (CalendarActivity calendarActivity : activities) {
            if (calendarActivity.getDate().equals(date)) {
                activitiesOnDate.add(calendarActivity);
            }
        }
        return activitiesOnDate;
    }
}
