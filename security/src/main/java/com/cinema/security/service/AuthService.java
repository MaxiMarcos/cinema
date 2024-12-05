package com.cinema.security.service;

import com.cinema.security.dto.AuthResponseDTO;
import com.cinema.security.dto.RegisterRequestDTO;
import com.cinema.security.dto.UserResponseDTO;
import com.cinema.security.entity.RoleName;

import java.util.List;

public interface AuthService {


    AuthResponseDTO create(RegisterRequestDTO registerRequestDTO, RoleName role);

    List<UserResponseDTO> findAll();
}
