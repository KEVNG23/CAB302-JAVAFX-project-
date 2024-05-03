import com.example.demo1.AccountModel.Account;
import com.example.demo1.AccountModel.SqliteAccountDAO;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProfileControllerTest {

    private ProfileController controller;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField emailField;
    private TextArea messageArea;

    @Mock
    private SqliteAccountDAO accountDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ProfileController();
        controller.setAccountDAO(accountDAO);
        usernameField = new TextField();
        passwordField = new PasswordField();
        emailField = new TextField();
        messageArea = new TextArea();
        controller.setUsernameField(usernameField);
        controller.setPasswordField(passwordField);
        controller.setEmailField(emailField);
        controller.setMessageArea(messageArea);
    }

    @Test
    public void testSaveChanges() {
        // Mock account
        Account account = new Account(1, "testuser", "test@example.com", "password");

        // user input
        usernameField.setText("newUsername");
        passwordField.setText("newPassword");
        emailField.setText("newEmail@example.com");

        when(accountDAO.getAccount("testuser")).thenReturn(account);

        // Call method to test
        controller.onSaveChanges();

        // Verify
        assertEquals("Changes saved successfully.", messageArea.getText());
        assertEquals("newUsername", account.getUsername());
        assertEquals("newPassword", account.getPassword());
        assertEquals("newEmail@example.com", account.getEmail());

        // Verify
        verify(accountDAO, times(1)).updateAccount(account);
    }
}
