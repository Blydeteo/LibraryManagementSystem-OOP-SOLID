package com.library.interfaces.repository;

import java.util.List;
import java.util.Map;

/**
 * IPermissionRepository interface defines contract for permission data access
 */
public interface IPermissionRepository {
    /**
     * Load all role-permission mappings from storage
     * @return Map of role to list of permissions
     */
    Map<String, List<String>> loadPermissions();
    
    /**
     * Get permissions for a specific role
     * @param role The role name
     * @return List of permissions for the role
     */
    List<String> getPermissionsForRole(String role);
}
