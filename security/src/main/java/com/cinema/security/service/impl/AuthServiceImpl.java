package com.cinema.security.service.impl;

import com.cinema.security.dto.AuthResponseDTO;
import com.cinema.security.dto.RegisterRequestDTO;
import com.cinema.security.dto.UserResponseDTO;
import com.cinema.security.entity.RoleName;
import com.cinema.security.entity.Token;
import com.cinema.security.entity.User;
import com.cinema.security.repository.AuthRepository;
import com.cinema.security.repository.TokenRepository;
import com.cinema.security.service.AuthService;
import com.cinema.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthRepository authRepo;
    @Autowired
    TokenRepository tokenRepo;
    @Autowired
    JwtService jwtService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO create(RegisterRequestDTO registerRequestDTO, RoleName role){

        User user = User.builder()
                .username(registerRequestDTO.getUsername())
                .email(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .role(role)
                .build();

        var savedUser = authRepo.save(user);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(savedUser, jwtToken);


        return AuthResponseDTO.builder()
                .jwt(jwtToken)
                .user(UserResponseDTO.builder()
                        .username(registerRequestDTO.getUsername())
                        .email(registerRequestDTO.getEmail())
                        .role(role)
                        .build())
                .build();

    }

    @Override
    public List<UserResponseDTO> findAll() {
         List<User> users = authRepo.findAll();

        return users.stream()
                .map(user -> new UserResponseDTO(user.getUsername(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());

    }


    private void saveUserToken(User user, String jwtToken){
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepo.save(token);
    }
}
