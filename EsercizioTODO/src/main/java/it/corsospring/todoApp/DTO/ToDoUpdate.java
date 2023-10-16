package it.corsospring.todoApp.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ToDoUpdate {
    @NotNull
    private int id;
    @NotBlank(message = "non puoi inserire un todo vuoto")
    private String todo;
    private LocalDate data;
    private boolean state;

    public ToDoUpdate(){

    }

    public ToDoUpdate(int id, String todo, LocalDate data, boolean state) {
        this.id = id;
        this.todo = todo;
        this.data = data;
        this.state=state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
