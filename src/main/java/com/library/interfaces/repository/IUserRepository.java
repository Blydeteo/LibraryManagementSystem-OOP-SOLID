package com.library.interfaces.repository;

import java.util.List;
import java.util.Map;

/**
 * IUserRepository interface defines contract for user data access
 */
public interface IUserRepository {
    /**
     * Load all users from storage
     * @return Map of username to UserCredentials
     */
    Map<String, UserCredentials> loadUsers();
    
    /**
     * Get user credentials by username
     * @param username The username to search
     * @return UserCredentials if found, null otherwise
     */
    UserCredentials findByUsername(String username);
    
    /**
     * DTO for user credentials
     */
    class UserCredentials {
        public final String userId;
        public final String username;
        public final String password;
        public final List<String> roles;
        
        public UserCredentials(String userId, String username, String password, List<String> roles) {
            this.userId = userId;
            this.username = username;
            this.password = password;
            this.roles = roles;
        }
    }
}
