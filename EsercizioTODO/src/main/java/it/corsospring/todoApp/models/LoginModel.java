package it.corsospring.todoApp.models;

import jakarta.validation.constraints.NotBlank;

public class LoginModel {
    @NotBlank(message = "non puo essere vuoto")
    private String username;
    @NotBlank(message = "non puo essere vuoto")
    private String password;

    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
