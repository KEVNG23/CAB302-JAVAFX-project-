import com.example.demo1.Models.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AccountTest {
    private Account account;
    @BeforeEach
    void setUp(){
        account = new Account( 1,"Testing", "testing@email.com", "testing123");

    }

    @Test
    void testGetId(){
        assertEquals(1, account.getId());

    }

    @Test
    void testSetId(){
        account.setId(2);
        assertEquals(2, account.getId());
    }

    @Test
    void testGetUsername(){
        assertEquals("Testing", account.getUsername());
    }

    @Test
    void testSetUsername(){
        account.setUsername("Testing2");
        assertEquals("Testing2", account.getUsername());
    }

    @Test
    void testGetEmail (){
        assertEquals("testing@email.com", account.getEmail());
    }

    @Test
    void testSetEmail(){
        account.setEmail("testing2@email.com");
        assertEquals("testing2@email.com", account.getEmail());
    }

    @Test
    void testGetPassword (){
        assertEquals("testing123", account.getPassword());
    }

    @Test
    void testSetPassword (){
        account.setPassword("testing1234");
        assertEquals("testing1234", account.getPassword());
    }

    @Test
    void testNullValue(){
        Account nullAccount = new Account(null, null, null, null);

        assertNull(nullAccount.getId());
        assertNull(nullAccount.getUsername());
        assertNull(nullAccount.getEmail());
        assertNull(nullAccount.getPassword());
    }
}
