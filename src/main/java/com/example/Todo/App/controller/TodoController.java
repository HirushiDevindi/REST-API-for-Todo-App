package com.example.Todo.App.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Todo.App.dto.TodoDto;
import com.example.Todo.App.entity.Todo;
import com.example.Todo.App.service.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    @Autowired
    private TodoService todoService;

    // Create a new Todo
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        Todo savedTodo = todoService.createTodo(todo);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    // Get all Todos for the authenticated user
    @GetMapping
    // public ResponseEntity<List<Todo>> getAllTodos() {
    //     List<Todo> todos = todoService.getAllTodos();
    //     return new ResponseEntity<>(todos, HttpStatus.OK);
    // }
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    // Get a single Todo by ID if it belongs to the authenticated user
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Todo todo = todoService.getTodoById(id);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    // Update an existing Todo if it belongs to the authenticated user
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        Todo updatedTodo = todoService.updateTodo(id, todoDetails);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }

    // Delete a Todo if it belongs to the authenticated user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id) {
        todoService.deleteTodoById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get Todos based on search criteria
    @GetMapping("/search")
    public ResponseEntity<List<TodoDto>> searchTodos(@RequestParam String keyword) {
        List<TodoDto> searchedTodos = todoService.searchTodos(keyword);
        return new ResponseEntity<>(searchedTodos, HttpStatus.OK);
    }
}
