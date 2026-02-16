package com.library.service;

import com.library.domain.entity.User;
import com.library.dto.AuthenticationResult;
import com.library.exception.AuthenticationException;
import com.library.interfaces.repository.IUserRepository;
import com.library.interfaces.service.IAuthenticationService;
import com.library.security.IPasswordEncoder;
import com.library.security.PlainTextPasswordEncoder;

/**
 * AuthenticationService handles user authentication with robust error handling.
 * 
 * Features:
 * - Input validation
 * - Secure password verification using password encoder
 * - Specific exception handling for different failure scenarios
 * - Extensible password encryption strategy
 * 
 * Depends on IUserRepository for data access and IPasswordEncoder for password security
 */
public class AuthenticationService implements IAuthenticationService {
    
    private final IUserRepository userRepository;
    private final IPasswordEncoder passwordEncoder;
    
    /**
     * Constructor with dependency injection
     * @param userRepository Repository for user data access
     */
    public AuthenticationService(IUserRepository userRepository) {
        this(userRepository, new PlainTextPasswordEncoder());
    }
    
    /**
     * Constructor with dependency injection for advanced password encoding
     * @param userRepository Repository for user data access
     * @param passwordEncoder Encoder for password verification (e.g., SHA256, BCrypt)
     */
    public AuthenticationService(IUserRepository userRepository, IPasswordEncoder passwordEncoder) {
        if (userRepository == null) {
            throw new IllegalArgumentException("UserRepository cannot be null");
        }
        if (passwordEncoder == null) {
            throw new IllegalArgumentException("PasswordEncoder cannot be null");
        }
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Authenticate user with username and password
     * @param username The username to authenticate
     * @param password The password to validate
     * @return AuthenticationResult containing the user if successful
     * @throws AuthenticationException if critical validation fails
     */
    @Override
    public AuthenticationResult authenticate(String username, String password) throws AuthenticationException {
        try {
            // Validate input
            validateInputs(username, password);
            
            // Fetch user from repository
            IUserRepository.UserCredentials credentials = userRepository.findByUsername(username);
            if (credentials == null) {
                // Return failure result instead of throwing exception
                return new AuthenticationResult("User '" + username + "' not found. Please check and try again.");
            }
            
            // Verify password using encoder
            if (!passwordEncoder.matches(password, credentials.password)) {
                return new AuthenticationResult("Invalid password for user '" + username + "'.");
            }
            
            // Authentication successful - create and return result
            User authenticatedUser = new User(credentials.username, credentials.password, credentials.roles);
            return new AuthenticationResult(authenticatedUser);
        } catch (AuthenticationException e) {
            // Return graceful failure result
            return new AuthenticationResult(e.getMessage());
        } catch (Exception e) {
            // Catch unexpected errors and convert to result
            System.err.println("Unexpected authentication error: " + e.getMessage());
            return new AuthenticationResult("An error occurred during authentication. Please try again.");
        }
    }
    
    /**
     * Validate input parameters
     * @throws AuthenticationException if inputs are invalid
     */
    private void validateInputs(String username, String password) throws AuthenticationException {
        if (username == null) {
            throw new AuthenticationException("Username cannot be null");
        }
        
        if (username.trim().isEmpty()) {
            throw new AuthenticationException("Username cannot be empty");
        }
        
        if (password == null) {
            throw new AuthenticationException("Password cannot be null");
        }
        
        if (password.isEmpty()) {
            throw new AuthenticationException("Password cannot be empty");
        }
    }
}
