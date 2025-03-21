package model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * UserManager handles registration, login, and persistence of user accounts.
 * User data (including each user's LibraryModel) is saved to and loaded from a file.
 */
public final class UserManager implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Map<String, UserAccount> users = new HashMap<>();
    private String filePath;
    
    /**
     * Default constructor uses "src/PasswordStorage/users.dat".
     */
    public UserManager() {
        this("src/PasswordStorage/users.dat");
    }
    
    /**
     * Constructor that accepts a custom file path.
     */
    public UserManager(String filePath) {
        this.filePath = filePath;
        loadUsers();
    }
    
    /**
     * Registers a new user.
     * @return true if registration is successful; false if username exists.
     */
    public boolean registerUser(String username, String password) {
        if(users.containsKey(username)) {
            return false;
        }
        UserAccount newUser = new UserAccount(username, password);
        users.put(username, newUser);
        saveUsers();
        return true;
    }
    
    /**
     * Logs in a user by verifying credentials.
     * @return The UserAccount if successful; otherwise, null.
     */
    public UserAccount login(String username, String password) {
        UserAccount user = users.get(username);
        if(user != null && user.verifyPassword(password)) {
            return user;
        }
        return null;
    }
    
    /**
     * Forces saving of user data.
     */
    public void save() {
        saveUsers();
    }
    
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadUsers() {
        File file = new File(filePath);
        if(!file.exists()){
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Map<String, UserAccount> loaded = (Map<String, UserAccount>) ois.readObject();
            users.putAll(loaded);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
