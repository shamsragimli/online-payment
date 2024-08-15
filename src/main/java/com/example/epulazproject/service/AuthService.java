package com.example.epulazproject.service;

import com.example.epulazproject.model.request.LoginReq;
import com.example.epulazproject.model.request.UserRequestDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
     ResponseEntity<?> authenticate(LoginReq loginReq);
     void register(UserRequestDto userRequestDto);

    }
