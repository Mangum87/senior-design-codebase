package com.example.myapplication.LoginStuff;

public class RegistrationResponse {
    private String auth_token;
    private String status;
    private String message;

    public RegistrationResponse(String auth_token, String status,String message) {
        this.auth_token = auth_token;
        this.status = status;
        this.message = message;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage(){
        return message;
    }
}
