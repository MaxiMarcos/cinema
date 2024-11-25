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
    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO) {

        User user = User.builder()
                .name(registerRequestDTO.getName())
                .email(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .role(RoleName.CUSTOMER)
                .build();

        var savedUser = authRepo.save(user);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(savedUser, jwtToken);


        return AuthResponseDTO.builder()
                .jwt(jwtToken)
                .user(UserResponseDTO.builder()
                        .name(registerRequestDTO.getName())
                        .email(registerRequestDTO.getEmail())
                        .role(RoleName.CUSTOMER)
                        .build())
                .build();

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
