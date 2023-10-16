package it.corsospring.todoApp.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SignUpModel {

    @Size(max = 20)
    private String name;
    @Size(max = 20)
    private String cognome;
    @Column(unique = true)
    private String email;

    @NotBlank(message = "non puo essere vuoto")
    @Size(max = 20)
    @Column(unique = true)
    private String username;
    @NotBlank(message = "non puo essere vuoto")
    private String password;

    public SignUpModel(String name, String cognome, String email, String username, String password) {
        this.name = name;
        this.cognome = cognome;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public SignUpModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
