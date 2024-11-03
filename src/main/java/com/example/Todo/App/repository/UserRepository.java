package com.example.Todo.App.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Todo.App.entity.Role;
import com.example.Todo.App.entity.user;

@Repository
public interface UserRepository extends JpaRepository<user,Long>{
    Optional<user> findByEmail(String email);

    user findByRole(Role role);

}
