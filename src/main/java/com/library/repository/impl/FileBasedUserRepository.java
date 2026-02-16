package com.library.repository.impl;

import com.library.interfaces.repository.IUserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileBasedUserRepository loads user data from a text file
 */
public class FileBasedUserRepository implements IUserRepository {
    
    private static final String USERS_FILE = "src/main/resources/User/UserDetail.txt";
    private Map<String, UserCredentials> usersCache;
    
    /**
     * Constructor - loads user data on initialization
     */
    public FileBasedUserRepository() {
        this.usersCache = new HashMap<>();
        loadUsers();
    }
    
    /**
     * Load user credentials from UserDetail.txt file
     * Format: userId|username|password|roles
     */
    @Override
    public Map<String, UserCredentials> loadUsers() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(USERS_FILE));
            
            if (lines.isEmpty()) {
                System.err.println("Warning: User file is empty: " + USERS_FILE);
                return usersCache;
            }
            
            // Skip header line: userId|username|password|roles
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) continue;
                
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 4) {
                        String userId = parts[0].trim();
                        String username = parts[1].trim();
                        String password = parts[2].trim();
                        String rolesStr = parts[3].trim();
                        List<String> roles = Arrays.asList(rolesStr.split(","));
                        
                        usersCache.put(username, new UserCredentials(userId, username, password, roles));
                    } else {
                        System.err.println("Warning: Invalid user record at line " + (i + 1) + ": " + line);
                    }
                } catch (Exception parseError) {
                    System.err.println("Warning: Error parsing line " + (i + 1) + ": " + parseError.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading user data from " + USERS_FILE + ": " + e.getMessage());
        }
        return usersCache;
    }
    
    /**
     * Get user credentials by username
     */
    @Override
    public UserCredentials findByUsername(String username) {
        return usersCache.get(username);
    }
}
