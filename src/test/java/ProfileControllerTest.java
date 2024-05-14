import com.example.demo1.AccountModel.Account;
import com.example.demo1.AccountModel.Session;
import com.example.demo1.AccountModel.SqliteAccountDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProfileControllerTest {

    private SqliteAccountDAO accountDAO;
    private Session session;  // Use Session instead of Preferences


    private MockedStatic<Session> mockedSession;


    @BeforeEach
    public void setup() {
        mockedSession = Mockito.mockStatic(Session.class);
        session = mock(Session.class);  // Mock the Session object
        accountDAO = mock(SqliteAccountDAO.class);  // Ensure this is correctly initialized


        // Set up a test account for the session
        Account loggedInAccount = new Account(1, "testUser", "password", "test@example.com");
        when(session.getLoggedInAccount()).thenReturn(loggedInAccount);

        // Mock Session.getInstance() to return your mocked session
        mockedSession.when(Session::getInstance).thenReturn(session);

    }



    @Test
    public void testSaveChanges() {

        // Setup mocks for account retrieval and update methods
        Account testAccount = new Account(1, "testUser", "password", "test@example.com");
        when(accountDAO.getAccount("testUser")).thenReturn(testAccount);

        // Simulate changing the password and email
        String newPassword = "newPassword";
        String newEmail = "newEmail@example.com";
        testAccount.setPassword(newPassword);
        testAccount.setEmail(newEmail);

        // Invoke the updateAccount method
        accountDAO.updateAccount(testAccount);

        // Verify that updateAccount was called correctly
        verify(accountDAO).updateAccount(testAccount);

        // Assert changes to ensure they're set as expected


        assertEquals(newPassword, testAccount.getPassword());
        assertEquals(newEmail, testAccount.getEmail());
    }



    @AfterEach
    public void tearDown() {
        mockedSession.close();  // Close the static mock
    }
}
