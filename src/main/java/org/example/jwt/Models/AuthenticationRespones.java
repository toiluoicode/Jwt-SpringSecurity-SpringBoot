package org.example.jwt.Models;

public class AuthenticationRespones {
    private String token;
    public AuthenticationRespones(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
