package com.raza.healthtracker.auth.service;

import com.raza.healthtracker.auth.dto.AuthResponse;
import com.raza.healthtracker.auth.dto.LoginRequest;
import com.raza.healthtracker.auth.dto.RegisterRequest;
import com.raza.healthtracker.auth.dto.UserMapper;
import com.raza.healthtracker.auth.dto.UserResponse;
import com.raza.healthtracker.auth.entity.Role;
import com.raza.healthtracker.auth.entity.User;
import com.raza.healthtracker.auth.repository.RoleRepository;
import com.raza.healthtracker.auth.repository.UserRepository;
import com.raza.healthtracker.auth.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use: " + request.getEmail());
        }

        String fullRoleName = "ROLE_PATIENT"; // Default role for registration

        Role role = roleRepository.findByName(fullRoleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + fullRoleName));

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .roles(Set.of(role))
                .build();

        User saved = userRepository.save(user);
        log.info("Registered new user: {} with role: {}", saved.getEmail(), fullRoleName);

        return UserMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return UserMapper.toResponseList(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAllByRole(String role) {
        String fullRoleName = normalizeRole(role);
        return UserMapper.toResponseList(
                userRepository.findAllByRoleName(fullRoleName)
        );
    }

    @Override
    @Transactional
    public UserResponse enableUser(Long id) {
        User user = getUser(id);
        user.setEnabled(true);
        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse disableUser(Long id) {
        User user = getUser(id);
        user.setEnabled(false);
        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .user(UserMapper.toResponse(user))
                .build();
    }

    private String normalizeRole(String role) {
        return role.startsWith("ROLE_") ? role : "ROLE_" + role.toUpperCase();
    }
}