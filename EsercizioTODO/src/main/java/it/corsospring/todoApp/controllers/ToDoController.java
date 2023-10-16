package it.corsospring.todoApp.controllers;

import it.corsospring.todoApp.DTO.ToDoResponse;
import it.corsospring.todoApp.DTO.ToDoSetStateRequest;
import it.corsospring.todoApp.DTO.ToDoUpdate;
import it.corsospring.todoApp.models.ToDoModel;
import it.corsospring.todoApp.DTO.ToDoRequest;
import it.corsospring.todoApp.models.UserModel;
import it.corsospring.todoApp.services.ToDoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.time.LocalDate;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/todo")
public class ToDoController {

    private final ToDoService toDoService;
    private final ModelMapper modelMapper;

    public ToDoController(ToDoService toDoService, ModelMapper modelMapper) {
        this.toDoService = toDoService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/showalltodo")
    ResponseEntity<Page<ToDoResponse>> showAllToDo(
            @PageableDefault(page=0,size = 8)
            Pageable pageable,UsernamePasswordAuthenticationToken auth){
        try{
            UserModel user=(UserModel) auth.getPrincipal();
            return new ResponseEntity(toDoService.findAllByUser(user,pageable),HttpStatus.OK);
        }
       // catch(ToDoException e ){
        //    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        //}
        catch (Exception e){
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/addtodo")
    ResponseEntity<ToDoResponse> addToDo(@Valid @RequestBody ToDoRequest toDoRequest, UsernamePasswordAuthenticationToken auth){
            UserModel userModel=(UserModel)auth.getPrincipal();
            ToDoModel todoCreate=toDoService.saveTodo(toDoRequest,userModel);
            return new ResponseEntity<>(modelMapper.map(todoCreate,ToDoResponse.class),HttpStatus.CREATED);
    }

    @PutMapping("/updatetodo/{id}")
    ResponseEntity<ToDoResponse> updateToDo(@PathVariable Long id, @Valid @RequestBody ToDoUpdate toDoUpdate, UsernamePasswordAuthenticationToken auth){
        try {
            UserModel user = (UserModel) auth.getPrincipal();
            ToDoModel UpdateToDo=toDoService.updateToDo(id,toDoUpdate,user);
            return new ResponseEntity<>(modelMapper.map(UpdateToDo,ToDoResponse.class),HttpStatus.OK);
        }
        catch (Exception e ){
            //e.getMessage();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findbydate")
    ResponseEntity<Page<ToDoResponse>> findByDate(UsernamePasswordAuthenticationToken auth,
                                                  @RequestParam(value="start",required = false, defaultValue = "#{T(java.time.Date).now()}")
                                                  @DateTimeFormat(pattern = "dd-MM-yyy" ) Date startDate,
                                                  @RequestParam(value="finish",required = false, defaultValue = "#{T(java.time.Date).now()}")
                                                  @DateTimeFormat(pattern = "dd-MM-yyy") Date finishDate,
                                                  @PageableDefault(page=0,size = 8) Pageable pageable
                                                  ){
        return new ResponseEntity<>(toDoService.findByDateBetween(auth,startDate,finishDate,pageable),HttpStatus.OK);
    }

    @PutMapping("/performed/{id}")
    ResponseEntity<ToDoResponse> wadPerformed(@PathVariable Long id, @Valid @RequestBody ToDoSetStateRequest toDoSetStateRequest, UsernamePasswordAuthenticationToken auth){
            UserModel user=(UserModel) auth.getPrincipal();
            ToDoModel wasPerformed=toDoService.wasPerformed(id,toDoSetStateRequest,user);
            return new ResponseEntity<>(modelMapper.map(wasPerformed,ToDoResponse.class),HttpStatus.OK);
    }

    @GetMapping("/findbystatus")
    ResponseEntity <Page<ToDoResponse>> findByStatus(@RequestParam(value="stato", required = false, defaultValue = "false") boolean stato,//mette a falso lo state se non è presente
                                                     UsernamePasswordAuthenticationToken auth,
                                                     @PageableDefault(page=0,size = 8) Pageable pageable){
        UserModel userModel= (UserModel) auth.getPrincipal();
        return new ResponseEntity<>(toDoService.findAllByState(userModel,pageable,stato),HttpStatus.OK);
    }


    @DeleteMapping("/deletetodo")
    ResponseEntity<String> deleteToDo(@PathVariable Long id, UsernamePasswordAuthenticationToken auth){
        try{
            UserModel userModel=(UserModel) auth.getPrincipal();
            toDoService.deleteToDo(id,userModel);
            return new ResponseEntity<>("eliminato",HttpStatus.OK);
        }catch (NoSuchElementException e){
           throw new NullPointerException("non ha trovato un todo con quell id");

        }

    }

}
