package com.example.myapplication.LoginStuff;

public class RegistrationResponse {
    private String auth_token;
    private String status;

    public RegistrationResponse(String auth_token, String status) {
        this.auth_token = auth_token;
        this.status = status;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public String getStatus() {
        return status;
    }
}
