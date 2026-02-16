package com.library.repository.impl;

import com.library.interfaces.repository.IPermissionRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileBasedPermissionRepository loads role-permission mappings from a text file
 */
public class FileBasedPermissionRepository implements IPermissionRepository {
    
    private static final String PERMISSIONS_FILE = "src/main/resources/Permission/RolePermission.txt";
    private Map<String, List<String>> permissionsCache;
    
    /**
     * Constructor - loads permission data on initialization
     */
    public FileBasedPermissionRepository() {
        this.permissionsCache = new HashMap<>();
        loadPermissions();
    }
    
    /**
     * Load role-permission mappings from RolePermission.txt file
     * Format: role|permissions
     */
    @Override
    public Map<String, List<String>> loadPermissions() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(PERMISSIONS_FILE));
            // Skip header line: role|permissions
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    String role = parts[0];
                    String permissionsStr = parts[1];
                    List<String> permissions = Arrays.asList(permissionsStr.split(","));
                    permissionsCache.put(role, new ArrayList<>(permissions));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading permission data from " + PERMISSIONS_FILE + ": " + e.getMessage());
        }
        return permissionsCache;
    }
    
    /**
     * Get permissions for a specific role
     */
    @Override
    public List<String> getPermissionsForRole(String role) {
        return permissionsCache.getOrDefault(role, new ArrayList<>());
    }
}
