import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.calendar.CalendarController;
import com.example.calendar.CalendarActivity;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.List;

public class CalendarControllerTest {

    private CalendarController calendarController;

    @Before
    public void setUp() {
        calendarController = new CalendarController();
    }

    @Test
    public void testAddAndGetActivityOnDate() {
        // Create a sample activity for the current date
        String titleToday = "Meeting Today";
        LocalDate today = LocalDate.now();
        String priorityToday = "URG";
        CalendarActivity activityToday = new CalendarActivity(null, titleToday, today, priorityToday);

        // Add activity for today
        calendarController.addActivity(activityToday);

        // Create activities for future and past dates
        LocalDate futureDate = today.plusDays(5);
        LocalDate pastDate = today.minusDays(3);

        String titleFuture = "Future Event";
        String priorityFuture = "AVE";
        CalendarActivity activityFuture = new CalendarActivity(null, titleFuture, futureDate, priorityFuture);

        String titlePast = "Past Event";
        String priorityPast = "Not URG";
        CalendarActivity activityPast = new CalendarActivity(null, titlePast, pastDate, priorityPast);

        // Add activities for future and past dates
        calendarController.addActivity(activityFuture);
        calendarController.addActivity(activityPast);

        // Retrieve activities for the current date (today)
        List <CalendarActivity> activitiesToday = calendarController.getActivitiesOnDate(today);

        // Validate activities for today
        assertEquals(1, activitiesToday.size());
        CalendarActivity retrievedToday = activitiesToday.get(0);
        assertEquals(titleToday, retrievedToday.getTitle());
        assertEquals(today, retrievedToday.getDate());
        assertEquals(priorityToday, retrievedToday.getPriority());

        // Retrieve activities for a future date
        List <CalendarActivity> activitiesFuture = calendarController.getActivitiesOnDate(futureDate);

        // Validate activities for the future date
        assertEquals(1, activitiesFuture.size());
        CalendarActivity retrievedFuture = activitiesFuture.get(0);
        assertEquals(titleFuture, retrievedFuture.getTitle());
        assertEquals(futureDate, retrievedFuture.getDate());
        assertEquals(priorityFuture, retrievedFuture.getPriority());

        // Retrieve activities for a past date
        List <CalendarActivity> activitiesPast = calendarController.getActivitiesOnDate(pastDate);

        // Validate activities for the past date
        assertEquals(1, activitiesPast.size());
        CalendarActivity retrievedPast = activitiesPast.get(0);
        assertEquals(titlePast, retrievedPast.getTitle());
        assertEquals(pastDate, retrievedPast.getDate());
        assertEquals(priorityPast, retrievedPast.getPriority());
    }

    @Test
    public void testAddActivityWithInvalidInput() {
        // Attempt to add an activity with empty title and date
        CalendarActivity invalidActivity = new CalendarActivity(null, null, null , "AVE");

        // Add the invalid activity
        calendarController.addActivity(invalidActivity);

        // Get activities for today's date
        LocalDate today = LocalDate.now();
        List<CalendarActivity> activities = calendarController.getActivitiesOnDate(today);

        // Assertions
        assertNotNull(activities);
        assertEquals(0, activities.size()); // No activity should be added due to invalid input
    }

    @Test
    public void testDrawCalendar() {
        // Add sample activities
        calendarController.addActivity(new CalendarActivity(null, "Meeting", LocalDate.now(), "URG"));
        calendarController.addActivity(new CalendarActivity(null, "Presentation", LocalDate.now().plusDays(1), "AVE"));

        // Ensure drawCalendar() does not throw exceptions
        assertDoesNotThrow(() -> calendarController.drawCalendar());
    }
}
