package com.cinema.segurity.service.impl;

import com.cinema.segurity.dto.RegisterRequestDTO;
import com.cinema.segurity.entity.RoleName;
import com.cinema.segurity.entity.User;
import com.cinema.segurity.repository.AuthRepository;
import com.cinema.segurity.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    AuthRepository authRepo;
    PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequestDTO registerRequestDTO) {

        // Implementar builder para refactorizar

        User user = new User();
        user.setName(registerRequestDTO.getName());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRole(RoleName.CUSTOMER);

        authRepo.save(user);

    }
}
