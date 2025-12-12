package com.library.interfaces.controller;

import com.library.domain.entity.User;

/**
 * InputInterface for Login use case
 */
public interface ILoginController {
    /**
     * Authenticate user
     * @param Username
     * @param Password
     * @return User if successful, null if failed
     */
    User login(String Username, String Password);
}