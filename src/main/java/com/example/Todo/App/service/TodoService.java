package com.example.Todo.App.service;

import java.util.List;

import com.example.Todo.App.dto.TodoDto;
import com.example.Todo.App.entity.Todo;

public interface TodoService {
    Todo createTodo(Todo todo);
    //List<Todo> getAllTodos();
    List<TodoDto> getAllTodos();
    Todo getTodoById(Long id);
    Todo updateTodo(Long id, Todo todoDetails);
    void deleteTodoById(Long id);
    List<TodoDto> searchTodos(String keyword);
}
