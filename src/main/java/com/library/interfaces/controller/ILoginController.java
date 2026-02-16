package com.library.interfaces.controller;

import com.library.dto.AuthenticationResult;

/**
 * ILoginController interface for login operations
 */
public interface ILoginController {
    /**
     * Authenticate user with username and password
     * @param username Username input
     * @param password Password input
     * @return AuthenticationResult containing authentication outcome
     */
    AuthenticationResult login(String username, String password);
}