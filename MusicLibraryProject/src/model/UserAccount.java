package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import model.LibraryModel;

/**
 * UserAccount represents a registered user.
 * It stores the username and a salted/hashed password and assigns a personal LibraryModel.
 */
public final class UserAccount implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private final String username;
    private final String passwordHash;
    private final String salt;
    
    // The user's music library.
    private final LibraryModel library;
    
    public UserAccount(String username, String password) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty()){
            throw new IllegalArgumentException("Username and password must not be empty.");
        }
        this.username = username;
        this.salt = generateSalt();  // Only generate salt once at creation
        this.passwordHash = hashPassword(password, this.salt);
        // Initialize the user's library, etc.
        this.library = new LibraryModel();
    }
    
    private String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] saltBytes = new byte[16];
        sr.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
    
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available.", e);
        }
    }
    
    // Verification:
    public boolean verifyPassword(String password) {
        // Do not generate a new salt—reuse the stored one.
        String hashed = hashPassword(password, this.salt);
        System.out.println("DEBUG: Stored hash: " + this.passwordHash);
        System.out.println("DEBUG: Computed hash: " + hashed);
        return hashed.equals(this.passwordHash);
    }
    
    public String getUsername() {
        return username;
    }
    
    /**
     * Returns the user's LibraryModel.
     */
    public LibraryModel getLibrary() {
        return library;
    }
    
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
    }
}
