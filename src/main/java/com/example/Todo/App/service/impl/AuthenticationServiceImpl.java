package com.example.Todo.App.service.impl;


import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Todo.App.dto.JwtAuthenticationResponse;
import com.example.Todo.App.dto.RefreshTokenRequest;
import com.example.Todo.App.dto.SignUpRequest;
import com.example.Todo.App.dto.SigninRequest;
import com.example.Todo.App.entity.Role;
import com.example.Todo.App.entity.user;
import com.example.Todo.App.repository.UserRepository;
import com.example.Todo.App.service.AuthenticationService;
import com.example.Todo.App.service.JWTService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager autheticationManager;
    private final JWTService jwtService;

    public user signup(SignUpRequest signupRequest){
        user u = new user();
        u.setEmail(signupRequest.getEmail());
        u.setFirstName(signupRequest.getFirstName());
        u.setLastName(signupRequest.getLastName());
        u.setRole(Role.USER);
        u.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        return userRepository.save(u);
        

    }

    public JwtAuthenticationResponse signin(SigninRequest signinRequest){
            
        autheticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), 
            signinRequest.getPassword()));
        var user = userRepository.findByEmail(signinRequest.getEmail())
                    .orElseThrow(()-> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
        
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
        
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserEmail(refreshTokenRequest.getToken());
        user user = userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
