package com.library.security;

/**
 * PasswordEncoder interface for secure password handling
 */
public interface IPasswordEncoder {
    
    /**
     * Encode a plain text password
     * @param rawPassword The plain text password
     * @return The encoded password
     */
    String encode(String rawPassword);
    
    /**
     * Match a raw password against an encoded password
     * @param rawPassword The plain text password to check
     * @param encodedPassword The encoded password to match against
     * @return true if passwords match, false otherwise
     */
    boolean matches(String rawPassword, String encodedPassword);
}
