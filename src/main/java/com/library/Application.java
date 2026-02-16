package com.library;

import com.library.ui.LoginUI;
import com.library.controller.LoginController;
import com.library.domain.entity.User;

public class Application {
    private final LoginUI LoginUI;
    private final LoginController LoginController;

    public Application() {
        // --- Initialize Controller ---
        LoginController = new LoginController();

        // --- Initialize UI ---
        LoginUI = new LoginUI(LoginController);
    }
    public void start() {
        // --- Login loop with retry limit ---
        int maxAttempts = 3;
        int attempts = 0;
        
        System.out.println("\n=== Welcome to Library Management System ===");
        System.out.println("Login attempts remaining: " + (maxAttempts - attempts));
        
        while (!LoginUI.isLoggedIn() && attempts < maxAttempts) {
            LoginUI.show();
            attempts++;
            
            if (!LoginUI.isLoggedIn() && attempts < maxAttempts) {
                System.out.println("\nLogin failed. Please try again.");
                System.out.println("Attempts remaining: " + (maxAttempts - attempts));
            }
        }
        
        // Check if login was successful
        if (LoginUI.isLoggedIn()) {
            User LoggedInUser = LoginUI.getUser();
            System.out.println("\n[Application]: Successfully logged in as: " + LoggedInUser.getUsername());
            System.out.println("[Application]: Your roles: " + LoggedInUser.getRoles());
        } else {
            System.out.println("\n[Application]: Authentication failed after " + maxAttempts + " attempts. Exiting.");
        }
        
        // Close the LoginUI scanner
        LoginUI.close();
    }
}