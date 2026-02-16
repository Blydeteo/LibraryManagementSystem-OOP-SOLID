package com.library.exception;

/**
 * Exception thrown when user is not found
 */
public class UserNotFoundException extends AuthenticationException {
    
    public UserNotFoundException(String username) {
        super("User '" + username + "' not found.");
    }
}
