import com.example.demo1.AccountModel.Account;
import com.example.demo1.AccountModel.Session;
import com.example.demo1.AccountModel.SqliteAccountDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProfileControllerTest {

    private SqliteAccountDAO accountDAO;
    private Session session;  // Use Session instead of Preferences

    @BeforeEach
    public void setup() {
        // Create a mock SqliteAccountDAO
        accountDAO = mock(SqliteAccountDAO.class);

        // Mock the Session to return a specific logged-in user
        session = mock(Session.class);
        Account loggedInAccount = new Account(1, "testUser", "password", "test@example.com");
        when(session.getLoggedInAccount()).thenReturn(loggedInAccount);

        // Ensure Session.getInstance() returns this mock session
        when(Session.getInstance()).thenReturn(session);
    }

    @Test
    public void testSaveChanges() {
        // Retrieve the logged-in account from the mocked session
        Account testAccount = session.getLoggedInAccount();

        // Assume that the user changes their password and email
        String newPassword = "newPassword";
        String newEmail = "newEmail@example.com";

        // Update the account with new details
        testAccount.setPassword(newPassword);
        testAccount.setEmail(newEmail);

        // Invoke the updateAccount method on DAO
        accountDAO.updateAccount(testAccount);

        // Verify the update was called correctly
        verify(accountDAO, times(1)).updateAccount(testAccount);

        // Assert that the account details were updated
        assertEquals(newPassword, testAccount.getPassword());
        assertEquals(newEmail, testAccount.getEmail());
    }
}
