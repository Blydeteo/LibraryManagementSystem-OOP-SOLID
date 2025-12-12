package com.library.domain.entity;

import java.util.List;

public class User {

    private final String username;
    private final String password;
    private final List<String> roles; // e.g., ADMIN, LIBRARIAN, MEMBER

    /**
     * Constructor for User entity
     *
     * @param username The username of the user
     * @param password The password of the user (consider hashing for real app)
     * @param roles    List of roles assigned to the user
     */
    public User(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    // --- Getters ---
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }

    // Optionally, you can override toString for debugging
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }
}
