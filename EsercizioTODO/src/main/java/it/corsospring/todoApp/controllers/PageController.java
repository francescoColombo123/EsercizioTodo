package it.corsospring.todoApp.controllers;

import it.corsospring.todoApp.models.UserModel;
import it.corsospring.todoApp.services.ToDoService;
import it.corsospring.todoApp.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PageController {

    private final ToDoService toDoService;

    public PageController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/public")
    public String publicPage() {
        return "Hello";
    }

    /*@GetMapping("/private")
    public String privatePage(UsernamePasswordAuthenticationToken auth) {
        UserModel user= (UserModel) auth.getPrincipal();//ti prende tutti i dati del singolo utente
        toDoService.showAll(user);
        return "Private page";
    }*/

    @GetMapping("/private")
    public String privatePage(UsernamePasswordAuthenticationToken auth) {
        return "private page";
    }

}
