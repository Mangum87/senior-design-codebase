package com.example.myapplication.LoginStuff;


/**
 * Create a LoginResponse Class to store the Response upon successful Login.
 * Check documentation with the heading POSTMAN RESPONSE to view the response from
 * the server.
 */
public class LoginResponse {
    /*
    Declare the varible names. Make sure the variables names must be exactly same
    as the variable  names returned by the server.
     */
    private String auth_token;
    private String message;
    private String status;
    private User user;


    public LoginResponse(String auth_token, String message, String  status, User user) {
        this.auth_token = auth_token;
        this.message = message;
        this.status = status;
        this.user=user;
    }

    public LoginResponse(String auth_token, String message, String status) {
        this.auth_token = auth_token;
        this.message = message;
        this.status = status;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
    public User getUser(){
        return user;
    }
}
