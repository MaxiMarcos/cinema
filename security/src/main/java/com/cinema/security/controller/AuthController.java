package com.cinema.security.controller;

import com.cinema.security.dto.AuthResponseDTO;
import com.cinema.security.dto.LoginRequestDTO;
import com.cinema.security.dto.RegisterRequestDTO;
import com.cinema.security.dto.UserResponseDTO;
import com.cinema.security.entity.RoleName;
import com.cinema.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register-customer")
    public ResponseEntity registerCustomer(@RequestBody RegisterRequestDTO registerRequestDTO, RoleName roleName) {

        RoleName role = RoleName.CUSTOMER;

        AuthResponseDTO authResponseDTO = authService.create(registerRequestDTO, role);


        if(authResponseDTO != null){
            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Hay un error", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/register-admin")
    public ResponseEntity registerAdmin(@RequestBody RegisterRequestDTO registerRequestDTO, RoleName roleName) {

        RoleName role = RoleName.ADMIN;

        AuthResponseDTO authResponseDTO = authService.create(registerRequestDTO, role);


        if(authResponseDTO != null){
            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Hay un error", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity authenticate (@RequestBody LoginRequestDTO loginRequestDTO){

        AuthResponseDTO authResponseDTO = authService.login(loginRequestDTO);

        if(authResponseDTO != null){
            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Hay un error", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){

        AuthResponseDTO authResponseDTO = authService.refreshToken(authHeader);
        if(authResponseDTO != null){
            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Hay un error", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/user/get-all")
    public ResponseEntity<?> findAll(){

        try {
            List<UserResponseDTO> users = authService.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}