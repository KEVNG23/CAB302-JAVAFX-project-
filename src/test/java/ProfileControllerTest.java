import com.example.demo1.AccountModel.Account;
import com.example.demo1.AccountModel.SqliteAccountDAO;
import com.example.demo1.ProfileController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.prefs.Preferences;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProfileControllerTest {

    private SqliteAccountDAO accountDAO;
    private Preferences preferences;

    @BeforeEach
    public void setup() {
        // Create a mock SqliteAccountDAO
        accountDAO = mock(SqliteAccountDAO.class);

        // Create a mock Preferences object to store the logged-in username
        preferences = mock(Preferences.class);
        when(preferences.get(ProfileController.getSessionUsernameKey(), null)).thenReturn("testUser");
    }

    @Test
    public void testSaveChanges() {
        // Create a test account
        Account testAccount = new Account(1, "testUser", "password", "test@example.com");

        // Mock behavior of accountDAO
        when(accountDAO.getAccount("testUser")).thenReturn(testAccount);

        // Simulate changing the password and email
        String newPassword = "newPassword";
        String newEmail = "newEmail@example.com";

        // Update the account with the new password and email
        testAccount.setPassword(newPassword);
        testAccount.setEmail(newEmail);

        // Call the updateAccount method
        accountDAO.updateAccount(testAccount);

        // Verify that the account details are updated
        verify(accountDAO, times(1)).updateAccount(testAccount);
        assertEquals(newPassword, testAccount.getPassword());
        assertEquals(newEmail, testAccount.getEmail());
    }
}
