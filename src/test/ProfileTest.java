import com.example.demo1.HelloController;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloControllerTest {

    private HelloController controller;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField emailField;
    private TextArea messageArea;

    @Test
    public void testChangeUsername() {
        clickOn(usernameField).write("newUsername");
        controller.onChangeUsername();
        assertEquals("New Username: newUsername", messageArea.getText());
    }

    @Test
    public void testChangePassword() {
        clickOn(passwordField).write("newPassword");
        controller.onChangePassword();
        assertEquals("New Password: newPassword", messageArea.getText());
    }

    @Test
    public void testChangeEmail() {
        clickOn(emailField).write("newEmail@example.com");
        controller.onChangeEmail();
        assertEquals("New Email: newEmail@example.com", messageArea.getText());
    }