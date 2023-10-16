package it.corsospring.todoApp.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class ToDoRequest {
    @NotBlank(message = "non puoi fare un todo vuoto")
    private String todo;
    @JsonFormat(pattern = "dd/MM/yyy")
    @FutureOrPresent(message = "hai inserito una data che è gia passata")
    private LocalDate date;

    private boolean state;

    public ToDoRequest(){

    }

    public ToDoRequest(String todo, LocalDate date, boolean state) {
        this.todo = todo;
        this.date = date;
        this.state=state;
    }

    public boolean getState() {
        return state;
    }

    //cosi che all'inizio viene settato sempre a false
    public void setState(boolean state) {
        this.state = false;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if(date.isAfter(LocalDate.now()))
        {
            this.date = date;
        }
        else{
            throw new NullPointerException("Inserito una data minore della data di oggi");
        }
    }
}
