
import com.example.demo1.Calendar.CalendarActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CalendarControllerTest {

    private CalendarActivity calendarActivity;

    @BeforeEach
    void setUp() {
        // Mock LocalDate for testing
        LocalDate testDate = LocalDate.parse("2024-08-23");

        // Create CalendarActivity instance with mock data
        calendarActivity = new CalendarActivity(1, "Testing", testDate, "URG");
    }

    @Test
    void TestGetId(){
        assertEquals(1, calendarActivity.getId());
    }

    @Test
    void TestSetId(){
        calendarActivity.setId(2);
        assertEquals(2, calendarActivity.getId());
    }

    @Test
    void TestGetTitle(){
        assertEquals("Testing", calendarActivity.getTitle());
    }

    @Test
    void TestSetTitle(){
        calendarActivity.setTitle("Testing2");
        assertEquals("Testing2", calendarActivity.getTitle());
    }

    @Test
    void TestGetPriority(){
        assertEquals("URG", calendarActivity.getPriority());
    }

    @Test
    void TestSetPriority(){
        calendarActivity.setPriority("AVG");
        assertEquals("AVG", calendarActivity.getPriority());
    }

}
