package it.corsospring.todoApp.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
public class ToDoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "non puo essere vuoto")
    private String todo;
    @JsonFormat(pattern="dd/MM/yyy")
    private LocalDate expireDate;


    private boolean state;
    @ManyToOne
   // @JsonIgnore//con quest'annotazione non fa vedere il campo quando viene richiesto
    private UserModel user;


    public ToDoModel(){

    }
    public ToDoModel(Long id, String todo, LocalDate expireDate, UserModel user, boolean state) {
        this.id = id;
        this.todo = todo;
        this.expireDate = expireDate;
        this.state=state;
        this.user = user;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
