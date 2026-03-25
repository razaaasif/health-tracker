package com.raza.healthtracker.auth.dto;

import com.raza.healthtracker.auth.entity.Role;
import com.raza.healthtracker.auth.entity.User;

import java.util.List;

public class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(User user) {
        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .enabled(user.getEnabled())
                .roles(roles)
                .createdOn(user.getCreatedAt().toString())
                .build();
    }

    public static List<UserResponse> toResponseList(List<User> users) {
        return users.stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}