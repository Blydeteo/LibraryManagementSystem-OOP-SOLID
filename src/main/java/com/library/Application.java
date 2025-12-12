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
        // --- Login loop ---
        while (!LoginUI.isLoggedIn()) {
            LoginUI.show();
        }

        // if we got no error on login, then we set the user globally
        User LoggedInUser = LoginUI.getUser();
        System.out.println("[Application]: Logged in as: " + LoggedInUser.getUsername() + ", ROLES: " + LoggedInUser.getRoles());
        //boolean LoggedIn = false;

        //while (!LoggedIn) {
        //    LoginUI.show();
        //}
    }
}