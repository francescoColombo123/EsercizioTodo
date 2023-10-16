package it.corsospring.todoApp.services;

import it.corsospring.todoApp.DTO.ToDoRequest;
import it.corsospring.todoApp.DTO.ToDoResponse;
import it.corsospring.todoApp.DTO.ToDoSetStateRequest;
import it.corsospring.todoApp.DTO.ToDoUpdate;
import it.corsospring.todoApp.models.ToDoModel;
import it.corsospring.todoApp.models.UserModel;
import it.corsospring.todoApp.repositories.ToDoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;
    private final ModelMapper modelMapper;


    public ToDoService(ToDoRepository toDoRepository, ModelMapper modelMapper) {
        this.toDoRepository = toDoRepository;
        this.modelMapper = modelMapper;
    }

    /*public List<ToDoModel> showAll(UserModel userModel) throws ToDoException {
        List<ToDoModel> lista=toDoRepository.findAllByUser(userModel);
        if(lista.isEmpty() ){
            throw new ToDoException("la lista è vuota");
        }
        return toDoRepository.findAllByUser(userModel);
    }*/

    public ToDoModel saveTodo(ToDoRequest toDoRequest, UserModel userModel){
        ToDoModel toDoModel=new ToDoModel();
        toDoModel.setTodo(toDoRequest.getTodo());
        toDoModel.setState(false);
        toDoModel.setExpireDate(toDoRequest.getDate());
        toDoModel.setUser(userModel);
        return toDoRepository.save(toDoModel);
    }

    public  boolean findById(Long id, UserModel userModel){
        return toDoRepository.existsByIdAndUser(id,userModel);
    }

    public ToDoModel updateToDo(Long id, ToDoUpdate toDoUpdate, UserModel userModel){
        if(toDoRepository.existsByIdAndUser(id,userModel)){
           ToDoModel toDoModel=new ToDoModel();
           toDoModel.setId(id);
           toDoModel.setTodo(toDoUpdate.getTodo());
           toDoModel.setState(toDoUpdate.isState());
           toDoModel.setExpireDate(toDoUpdate.getData());
           return toDoRepository.save(toDoModel);
        }
        else throw new NullPointerException();
    }

    public ToDoModel wasPerformed(Long id, ToDoSetStateRequest toDoSetStateRequest, UserModel userModel){
           ToDoModel toDoModelOptional=toDoRepository.findByIdAndUser(id,userModel).orElseThrow(() -> new NoSuchElementException("toDo non trovato"));
            toDoModelOptional.setState(toDoSetStateRequest.isState());
            return toDoRepository.save(toDoModelOptional);
    }

    public Page<ToDoResponse> findByDateBetween(UsernamePasswordAuthenticationToken auth, Date startDate, Date finishDate, Pageable pageable){
        UserModel user=(UserModel) auth.getPrincipal();
        LocalDate start=startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate finish=finishDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return toDoRepository.findAllByUserAndExpireDateBetween(user,start,finish,pageable)
                    .map(todo -> modelMapper.map(todo,ToDoResponse.class));
    }

    public String deleteToDo(Long id,UserModel userModel){
        if(toDoRepository.existsByIdAndUser(id,userModel)){
            toDoRepository.deleteById(id);
            return "to do eliminato";
        }
        else{
            throw new NullPointerException();
        }

    }

    public Page<ToDoResponse> findAllByState(UserModel userModel, Pageable pageable,boolean stato) {
        PageRequest pages = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<ToDoModel> todos=toDoRepository.findAllByUserAndState(userModel,stato,pages);
        return todos.map(todo -> modelMapper.map(todo,ToDoResponse.class));
    }

    public Page<ToDoResponse> findAllByUser(UserModel userModel, Pageable pageable) {
        PageRequest pages = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return this.toDoRepository.findAllByUser(userModel, pageable)
                .map(todo -> modelMapper.map(todo,ToDoResponse.class));

    }

}
