import com.example.demo1.Timer.StopwatchController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimerTest {

    @Test
    public void testStartStopwatch() {
        StopwatchController controller = new StopwatchController();
        controller.onStopStartClick(); // Start the stopwatch
        // Assuming some delay in starting the stopwatch
        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long elapsedTime = getElapsedTime(controller);
        // Assuming some tolerance for timing accuracy
        long expectedElapsedTime = 1000; // 1 second
        long tolerance = 100; // Tolerance of 100 milliseconds
        assertEquals(expectedElapsedTime, elapsedTime, tolerance);
    }

    @Test
    public void testPauseStopwatch() {
        StopwatchController controller = new StopwatchController();
        controller.onStopStartClick(); // Start the stopwatch
        // Assuming some delay in pausing the stopwatch
        try {
            Thread.sleep(2000); // Wait for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.onStopStartClick(); // Pause the stopwatch
        long elapsedTime = getElapsedTime(controller);
        // Assuming some tolerance for timing accuracy
        long expectedElapsedTime = 2000; // 2 seconds
        long tolerance = 100; // Tolerance of 100 milliseconds
        assertEquals(expectedElapsedTime, elapsedTime, tolerance);
    }
    private long getElapsedTime(StopwatchController controller) {
        String[] timeParts = controller.stopwatchLabel.getText().split(":");
        long minutes = Long.parseLong(timeParts[0]);
        String[] secondsParts = timeParts[1].split("\\.");
        long seconds = Long.parseLong(secondsParts[0]);
        long milliseconds = Long.parseLong(secondsParts[1]) * 10; // Convert to milliseconds
        return minutes * 60 * 1000 + seconds * 1000 + milliseconds;
    }
}

