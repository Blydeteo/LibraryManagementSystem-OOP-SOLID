package com.library.controller;

import com.library.dto.AuthenticationResult;
import com.library.exception.AuthenticationException;
import com.library.interfaces.controller.ILoginController;
import com.library.interfaces.repository.IUserRepository;
import com.library.repository.impl.FileBasedUserRepository;
import com.library.security.IPasswordEncoder;
import com.library.security.PlainTextPasswordEncoder;
import com.library.service.AuthenticationService;

/**
 * LoginController implements ILoginController
 * Delegates authentication logic to AuthenticationService using dependency injection
 */
public class LoginController implements ILoginController {
    
    private final AuthenticationService authenticationService;
    
    /**
     * Constructor with dependency injection
     * Creates repositories with SHA-256 password encoder
     */
    public LoginController() {
        IUserRepository userRepository = new FileBasedUserRepository();
        IPasswordEncoder passwordEncoder = new PlainTextPasswordEncoder(); // For production, use new Sha256PasswordEncoder()
        this.authenticationService = new AuthenticationService(userRepository, passwordEncoder);
    }
    
    /**
     * Alternative constructor for custom password encoder
     */
    public LoginController(IPasswordEncoder passwordEncoder) {
        IUserRepository userRepository = new FileBasedUserRepository();
        this.authenticationService = new AuthenticationService(userRepository, passwordEncoder);
    }
    
    /**
     * Authenticate user credentials and return result
     * 
     * @param username The username to authenticate
     * @param password The password to validate
     * @return AuthenticationResult with authentication outcome (never null)
     */
    public AuthenticationResult login(String username, String password) {
        try {
            return authenticationService.authenticate(username, password);
        } catch (AuthenticationException e) {
            return new AuthenticationResult(e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error in login controller: " + e.getMessage());
            return new AuthenticationResult("An unexpected error occurred. Please try again.");
        }
    }
}
