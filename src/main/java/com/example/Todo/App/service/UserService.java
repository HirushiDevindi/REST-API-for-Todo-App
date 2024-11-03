package com.example.Todo.App.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.Todo.App.entity.user;

public interface UserService {

    UserDetailsService userDetailsService();
    user getAuthenticatedUser();

}
