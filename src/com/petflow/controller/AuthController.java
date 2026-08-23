package com.petflow.controller;
import com.petflow.database.UserDB;
import com.petflow.model.User;

public class AuthController {
    private UserDB userDB = new UserDB();
    
    public User login(String username, String password) {
        return userDB.login(username, password);
    }
}