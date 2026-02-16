package com.library.dto;

import com.library.domain.entity.User;

/**
 * AuthenticationResult DTO represents the outcome of an authentication attempt
 */
public class AuthenticationResult {
    
    private final boolean success;
    private final User authenticatedUser;
    private final String message;
    
    /**
     * Constructor for successful authentication
     */
    public AuthenticationResult(User authenticatedUser) {
        this.success = true;
        this.authenticatedUser = authenticatedUser;
        this.message = "Authentication successful";
    }
    
    /**
     * Constructor for failed authentication
     */
    public AuthenticationResult(String failureMessage) {
        this.success = false;
        this.authenticatedUser = null;
        this.message = failureMessage;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public User getAuthenticatedUser() {
        return authenticatedUser;
    }
    
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return "AuthenticationResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                (authenticatedUser != null ? ", user='" + authenticatedUser.getUsername() + '\'' : "") +
                '}';
    }
}
