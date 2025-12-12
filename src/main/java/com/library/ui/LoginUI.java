package com.library.ui;

import java.util.Scanner;

import com.library.domain.entity.User;

import com.library.interfaces.controller.ILoginController;

public class LoginUI {

    private final ILoginController LoginController;
    private User User;

    /**
     * Constructor
     * @param LoginController the controller implementing ILoginController
     */
    public LoginUI(ILoginController LoginController) {
        this.LoginController = LoginController;
    }

    /**
     * Show login prompt, read input, call controller, and store logged-in user
     */
    public void show() {
        Scanner Scanner = new Scanner(System.in);

        System.out.println("\n=== Library Management System Login ===");
        System.out.print("Username: ");
        String Username = Scanner.nextLine();

        System.out.print("Password: ");
        String Password = Scanner.nextLine();

        // Call controller to handle login
        User = LoginController.login(Username, Password);

        //if (User != null) {
        //    System.out.println("Login successful!");
        //} else {
        //    System.out.println("Login failed. Please try again.");
        //}
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
}