package com.example.Todo.App.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Todo.App.entity.Todo;
import com.example.Todo.App.entity.user;
import com.example.Todo.App.repository.TodoRepository;
import com.example.Todo.App.service.TodoService;

import lombok.RequiredArgsConstructor;
import com.example.Todo.App.dto.TodoDto;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserServiceImpl userService;

    public Todo createTodo(Todo todo) {
        user currentUser = userService.getAuthenticatedUser();
        todo.setUser(currentUser);
        return todoRepository.save(todo);
    }

    // public List<Todo> getAllTodos() {
    //     user currentUser = userService.getAuthenticatedUser();
    //     return todoRepository.findAll().stream()
    //             .filter(todo -> todo.getUser().getId().equals(currentUser.getId()))
    //             .collect(Collectors.toList());
    // }

    public List<TodoDto> getAllTodos() {
        user currentUser = userService.getAuthenticatedUser();
        return todoRepository.findAll().stream()
                .filter(todo -> todo.getUser().getId().equals(currentUser.getId()))
                .map(todo -> new TodoDto(
                        todo.getTaskId(),
                        todo.getTask(),
                        todo.isCompleted(),
                        todo.getUser().getId()
                ))
                .collect(Collectors.toList());
    }

    public Todo getTodoById(Long id) {
        user currentUser = userService.getAuthenticatedUser();
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));

        if (!todo.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        return todo;
    }

    public Todo updateTodo(Long id, Todo todoDetails) {
        user currentUser = userService.getAuthenticatedUser();
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));

        if (!todo.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        todo.setTask(todoDetails.getTask());
        todo.setCompleted(todoDetails.isCompleted());

        return todoRepository.save(todo);
    }

    public void deleteTodoById(Long id) {
        user currentUser = userService.getAuthenticatedUser();
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));

        if (!todo.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        todoRepository.deleteById(id);
    }

    public List<TodoDto> searchTodos(String keyword) {
        user currentUser = userService.getAuthenticatedUser();
    
        return todoRepository.findAll().stream()
                .filter(todo -> todo.getUser().getId().equals(currentUser.getId()))
                .filter(todo -> todo.getTask().toLowerCase().contains(keyword.toLowerCase()))
                .map(todo -> new TodoDto(todo.getTaskId(), todo.getTask(), todo.isCompleted(), todo.getUser().getId()))
                .collect(Collectors.toList());
    }
}
