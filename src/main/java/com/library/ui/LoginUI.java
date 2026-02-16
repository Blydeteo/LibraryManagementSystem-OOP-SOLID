package com.library.ui;

import java.util.Scanner;

import com.library.domain.entity.User;

import com.library.dto.AuthenticationResult;
import com.library.interfaces.controller.ILoginController;

public class LoginUI {

    private final ILoginController LoginController;
    private final Scanner scanner;
    private User User;

    /**
     * Constructor - initializes Scanner for persistent input handling
     * @param LoginController the controller implementing ILoginController
     */
    public LoginUI(ILoginController LoginController) {
        this.LoginController = LoginController;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Show login prompt, read input, call controller, and store logged-in user
     */
    public void show() {
        try {
            System.out.println("\n=== Library Management System Login ===");
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            // Call controller to handle login
            AuthenticationResult result = LoginController.login(username, password);

            if (result.isSuccess()) {
                User = result.getAuthenticatedUser();
                System.out.println("\n✓ " + result.getMessage());
                System.out.println("Your roles: " + User.getRoles());
            } else {
                User = null;
                System.out.println("\n✗ " + result.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error reading input: " + e.getMessage());
            User = null;
        }
    }

    /**
     * Get the logged-in user
     */
    public User getUser() {
        return User;
    }

    /**
     * Check if login was successful
     */
    public boolean isLoggedIn() {
        return User != null;
    }
    
    /**
     * Close the scanner when application shuts down
     */
    public void close() {
        try {
            if (scanner != null) {
                scanner.close();
            }
        } catch (Exception e) {
            // Ignore errors during close
        }
    }
}