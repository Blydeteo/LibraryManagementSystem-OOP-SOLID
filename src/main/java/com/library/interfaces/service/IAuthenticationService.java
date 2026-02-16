package com.library.interfaces.service;

import com.library.dto.AuthenticationResult;
import com.library.exception.AuthenticationException;

/**
 * IAuthenticationService defines contract for authentication operations
 */
public interface IAuthenticationService {
    
    /**
     * Authenticate user with username and password
     * @param username The username to authenticate
     * @param password The password to validate
     * @return AuthenticationResult containing authentication outcome
     * @throws AuthenticationException if authentication fails with validation errors
     */
    AuthenticationResult authenticate(String username, String password) throws AuthenticationException;
}
