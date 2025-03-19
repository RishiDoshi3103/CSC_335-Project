package model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, UserAccount> users = new HashMap<>();
    private final String filePath = "src/PasswordStorage/users.dat";  // File to store user data

    public UserManager(String testFilePath) {
        loadUsers();
    }

    /**
     * Registers a new user.
     * Returns false if the username already exists.
     */
    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;  // Username already taken
        }
        UserAccount newUser = new UserAccount(username, password);
        users.put(username, newUser);
        saveUsers();
        return true;
    }

    /**
     * Logs in a user. Returns the UserAccount if successful, otherwise null.
     */
    public UserAccount login(String username, String password) {
        UserAccount user = users.get(username);
        if (user != null && user.verifyPassword(password)) {
            return user;
        }
        return null;  // Login failed
    }

    /**
     * Saves the user data to a file.
     */
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the user data from a file.
     */
    @SuppressWarnings("unchecked")
    private void loadUsers() {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            users = (Map<String, UserAccount>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
