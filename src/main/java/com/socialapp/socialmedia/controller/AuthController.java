package com.socialapp.socialmedia.controller;

import com.socialapp.socialmedia.dto.LoginRequest;
import com.socialapp.socialmedia.dto.LoginResponse;
import com.socialapp.socialmedia.dto.RegisterRequest;
import com.socialapp.socialmedia.model.User;
import com.socialapp.socialmedia.security.JwtUtil;
import com.socialapp.socialmedia.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    // Register new user
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
        // Create user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setBio(request.getBio());

        User createdUser = userService.createUser(user);

        // Generate token
        String token = jwtUtil.generateToken(createdUser.getUsername(), createdUser.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("token", token);
        response.put("user", Map.of(
                "id", createdUser.getId(),
                "username", createdUser.getUsername(),
                "email", createdUser.getEmail(),
                "bio", createdUser.getBio()
        ));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Login existing user
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Get user details
            User user = userService.getUserByUsername(request.getUsername());

            // Generate token
            String token = jwtUtil.generateToken(user.getUsername(), user.getId());

            LoginResponse response = new LoginResponse(
                    token,
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getBio(),
                    user.getAvatarUrl()
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    // Test endpoint to verify token
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Remove "Bearer "
        String username = jwtUtil.extractUsername(token);
        Long userId = jwtUtil.extractUserId(token);

        User user = userService.getUserByUsername(username);

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("bio", user.getBio());
        response.put("avatarUrl", user.getAvatarUrl());

        return ResponseEntity.ok(response);
    }
}