package com.cinema.security.service.impl;

import com.cinema.security.dto.AuthResponseDTO;
import com.cinema.security.dto.LoginRequestDTO;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDTO create(RegisterRequestDTO registerRequestDTO, RoleName roleName){

        User user = User.builder()
                .username(registerRequestDTO.getUsername())
                .email(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .role(roleName)
                .build();

        var savedUser = authRepo.save(user);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(savedUser, jwtToken);


        return AuthResponseDTO.builder()
                .jwt(jwtToken)
                .refreshToken(refreshToken)
                .build();

    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );
        var user = authRepo.findByEmail(loginRequestDTO.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return AuthResponseDTO.builder()
                .jwt(jwtToken)
                .refreshToken(refreshToken)
                .build();

    }

    public AuthResponseDTO refreshToken (String authHeader){
        if(authHeader == null || !authHeader.startsWith("Bearer ")){

            throw new IllegalArgumentException("Invalid Bearer token");
        }

        String refreshToken = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(refreshToken);

        if(userEmail == null){
            throw  new IllegalArgumentException("Invalid refresh token");
        }

        User user = authRepo.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(userEmail));

        if(!jwtService.isTokenValid(refreshToken, user)){
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String accessToken = jwtService.generateToken(user);
        saveUserToken(user, accessToken);
        return new AuthResponseDTO(accessToken, refreshToken);

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
