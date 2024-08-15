package com.example.epulazproject.controller;

import com.example.epulazproject.model.request.LoginReq;
import com.example.epulazproject.model.request.UserRequestDto;
import com.example.epulazproject.service.serviceImpl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    @Operation(summary = "Login", description = "Authenticate with username and password")
    @ResponseBody
    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReq loginReq)  {
        return authService.authenticate(loginReq);
    }

    @Operation(summary = "User Registration", description = "Register a new user")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid UserRequestDto userRequestDto)  {
        authService.register(userRequestDto);
    }
}
