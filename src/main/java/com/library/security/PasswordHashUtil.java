package com.library.security;

/**
 * PasswordHashUtil - Utility class to generate password hashes
 * Use this to convert plaintext passwords to SHA-256 hashes for storage in UserDetail.txt
 */
public class PasswordHashUtil {
    
    private static final IPasswordEncoder ENCODER = new Sha256PasswordEncoder();
    
    /**
     * Generate SHA-256 hash for a password
     * @param plainPassword The plain text password
     * @return The hashed password
     */
    public static String hashPassword(String plainPassword) {
        return ENCODER.encode(plainPassword);
    }
    
    /**
     * Main method to hash passwords from command line
     * Usage: java PasswordHashUtil <plainPassword>
     * 
     * Example:
     *   java PasswordHashUtil 1234
     *   java PasswordHashUtil libpass
     *   java PasswordHashUtil pass123
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java PasswordHashUtil <plainPassword>");
            System.err.println("\nExample:");
            System.err.println("  java PasswordHashUtil 1234");
            System.err.println("  java PasswordHashUtil mypassword");
            System.exit(1);
        }
        
        String plainPassword = args[0];
        String hashedPassword = hashPassword(plainPassword);
        
        System.out.println("Plain Password: " + plainPassword);
        System.out.println("Hashed Password: " + hashedPassword);
        System.out.println("\nUse the hashed password in your UserDetail.txt file");
    }
}
