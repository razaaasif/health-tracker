package com.raza.healthtracker.auth.graphql;

import com.raza.healthtracker.auth.dto.AuthResponse;
import com.raza.healthtracker.auth.dto.LoginRequest;
import com.raza.healthtracker.auth.dto.RegisterRequest;
import com.raza.healthtracker.auth.dto.UserResponse;
import com.raza.healthtracker.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserGraphQLController {

    private final UserService userService;

    // ─── Queries ─────────────────────────────────────────────────────────────

    @QueryMapping
    public UserResponse user(@Argument Long id) {
        return userService.findById(id);
    }

    @QueryMapping
    public UserResponse userByEmail(@Argument String email) {
        return userService.findByEmail(email);
    }

    @QueryMapping
    public List<UserResponse> allUsers() {
        return userService.findAll();
    }

    @QueryMapping
    public List<UserResponse> usersByRole(@Argument String role) {
        return userService.findAllByRole(role);
    }

    // ─── Mutations ───────────────────────────────────────────────────────────

    @MutationMapping
    public String register(@Argument RegisterRequest input) {
        UserResponse user = userService.register(input);
        return "User registered with ID: " + user.getId();
    }

    @MutationMapping
    public AuthResponse login(@Argument LoginRequest input) {
        return userService.login(input);
    }

    @MutationMapping
    public UserResponse enableUser(@Argument Long id) {
        return userService.enableUser(id);
    }

    @MutationMapping
    public UserResponse disableUser(@Argument Long id) {
        return userService.disableUser(id);
    }

    @MutationMapping
    public Boolean deleteUser(@Argument Long id) {
        userService.deleteUser(id);
        return true;
    }
}