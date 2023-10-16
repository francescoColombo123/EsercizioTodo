package it.corsospring.todoApp.repositories;

import it.corsospring.todoApp.models.ToDoModel;
import it.corsospring.todoApp.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ToDoRepository extends JpaRepository<ToDoModel, Long>, PagingAndSortingRepository<ToDoModel,Long> {

    Page<ToDoModel> findAllByUser(UserModel userModel, Pageable pageable);
    List<ToDoModel> findAllByUser(UserModel user);

    Optional<ToDoModel> findByIdAndUser(Long id,UserModel user);

    Page<ToDoModel> findAllByUserAndState(UserModel userModel, boolean state, Pageable pageable);

    Page<ToDoModel> findAllByUserAndExpireDateBetween(UserModel userModel, LocalDate datastart,LocalDate datafinish,Pageable pageable);

    boolean existsByIdAndUser(Long id, UserModel user);





}
