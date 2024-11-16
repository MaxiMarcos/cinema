package com.cinema.segurity.controller;

import com.cinema.segurity.dto.RegisterRequestDTO;
import com.cinema.segurity.entity.User;
import com.cinema.segurity.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequestDTO registerRequestDTO) {

        authService.register(registerRequestDTO);
    }

}