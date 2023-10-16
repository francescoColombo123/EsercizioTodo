package it.corsospring.todoApp.DTO;

import java.time.LocalDate;

public class ToDoResponse {

    private  Long id;

    private String todo;
    private LocalDate expireDate;

    private boolean state;

    public ToDoResponse(){

    }

    public ToDoResponse(Long id, String todo, LocalDate expireDate, boolean state) {
        this.id = id;
        this.todo = todo;
        this.expireDate = expireDate;
        this.state = state;
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

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
