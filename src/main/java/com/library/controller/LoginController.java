package com.library.controller;

import com.library.domain.entity.User;

import com.library.interfaces.controller.ILoginController;

import java.util.Set;
import java.util.List;

/**
 * LoginController implements InputPort
 */
public class LoginController implements ILoginController {
    /**
     * Constructor
     */
    public LoginController() {
    }
    /**
     * UI calls this method to authenticate user
     */
    //@Override
    public User login(String username, String password) {
        User user = new User(username, password, List.of("ADMIN"));
        //System.out.println("LogIn as :" + username);
        //System.out.println("Password is: " + password);
        //User user = loginUseCase.login(username, password);

        //if (user != null) {
            // Post-login actions
            //Set<String> permissions = userService.processAfterLogin(user);
            // Optionally attach permissions to user
        //}

        return user;
    }
}
