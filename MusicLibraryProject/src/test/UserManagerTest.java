package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.UserManager;
import model.UserAccount;

import java.io.File;


class UserManagerTest {

	// Use a dedicated file for testing so that you don't interfere with production data.
    private static final String TEST_FILE_PATH = "src/PasswordStorage/test_users.dat";
    private UserManager userManager;
    
    @BeforeEach
    void setup() {
        // Initialize UserManager with the test file path.
        userManager = new UserManager(TEST_FILE_PATH);
    }
    
    @AfterEach
    void tearDown() {
        // Delete the test file after each test to ensure isolation.
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
    
    @Test
    void testRegisterUser() {
        // Test successful registration.
        assertTrue(userManager.registerUser("user1", "pass1"), "User should register successfully.");
        
        // Test duplicate registration.
        assertFalse(userManager.registerUser("user1", "anotherPass"), "Duplicate registration should fail.");
    }
    
    @Test
    void testLogin() {
        // Register a user.
        userManager.registerUser("user2", "pass2");
        
        // Test login with correct credentials.
        UserAccount user = userManager.login("user2", "pass2");
        assertNotNull(user, "User should be able to login with correct credentials.");
        assertEquals("user2", user.getUsername(), "Username should match.");
        
        // Test login with incorrect password.
        assertNull(userManager.login("user2", "wrongPass"), "Login with incorrect password should fail.");
    }
    
    @Test
    void testPersistence() {
        // Register a user and ensure data is saved.
        userManager.registerUser("user3", "pass3");
        
        // Create a new instance of UserManager with the same test file.
        UserManager newUserManager = new UserManager(TEST_FILE_PATH);
        
        // The user should be persisted and able to log in.
        UserAccount user = newUserManager.login("user3", "pass3");
        assertNotNull(user, "User should be persisted and able to login in new instance.");
    }

}
