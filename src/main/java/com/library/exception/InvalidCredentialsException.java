package com.library.exception;

/**
 * Exception thrown when user credentials are invalid
 */
public class InvalidCredentialsException extends AuthenticationException {
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
