package com.raza.healthtracker.auth.service;

import com.raza.healthtracker.auth.dto.AuthResponse;
import com.raza.healthtracker.auth.dto.LoginRequest;
import com.raza.healthtracker.auth.dto.RegisterRequest;
import com.raza.healthtracker.auth.dto.UserResponse;
import com.raza.healthtracker.auth.entity.User;

import java.util.List;
import java.util.Optional;
public interface UserService {

    UserResponse register(RegisterRequest request);

    UserResponse findById(Long id);

    UserResponse findByEmail(String email);

    List<UserResponse> findAll();

    List<UserResponse> findAllByRole(String role);

    UserResponse enableUser(Long id);

    UserResponse disableUser(Long id);

    void deleteUser(Long id);

    AuthResponse login(LoginRequest request);
}