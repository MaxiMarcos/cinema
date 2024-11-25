package com.cinema.security.controller;

import com.cinema.security.dto.AuthResponseDTO;
import com.cinema.security.dto.RegisterRequestDTO;
import com.cinema.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO registerRequestDTO) {

        AuthResponseDTO authResponseDTO = authService.register(registerRequestDTO);


        if(authResponseDTO != null){
            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Hay un error", HttpStatus.NO_CONTENT);
        }
    }

}