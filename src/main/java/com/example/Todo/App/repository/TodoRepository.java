package com.example.Todo.App.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Todo.App.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{

}
