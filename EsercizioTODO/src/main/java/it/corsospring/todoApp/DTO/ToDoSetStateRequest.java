package it.corsospring.todoApp.DTO;

public class ToDoSetStateRequest {

    private boolean state;

    public ToDoSetStateRequest(){

    }

    public ToDoSetStateRequest(boolean state) {
        this.state = state;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
