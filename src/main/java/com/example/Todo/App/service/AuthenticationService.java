package com.example.Todo.App.service;

import com.example.Todo.App.dto.JwtAuthenticationResponse;
import com.example.Todo.App.dto.RefreshTokenRequest;
import com.example.Todo.App.dto.SignUpRequest;
import com.example.Todo.App.dto.SigninRequest;
import com.example.Todo.App.entity.user;

public interface AuthenticationService {
    user signup(SignUpRequest signupRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
