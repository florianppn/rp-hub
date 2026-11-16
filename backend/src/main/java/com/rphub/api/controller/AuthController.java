package com.rphub.api.controller;

import com.rphub.api.dto.request.LoginRequest;
import com.rphub.api.dto.request.UserCreateRequest;
import com.rphub.api.dto.response.TokenResponse;
import com.rphub.api.dto.response.UserResponse;
import com.rphub.api.service.AuthService;
import com.rphub.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public TokenResponse login(
       @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(
       @Valid @RequestBody UserCreateRequest request
    ) {
        return userService.create(request);
    }
}