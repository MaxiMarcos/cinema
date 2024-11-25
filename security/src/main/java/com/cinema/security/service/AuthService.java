package com.cinema.security.service;

import com.cinema.security.dto.AuthResponseDTO;
import com.cinema.security.dto.RegisterRequestDTO;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO registerRequestDTO);
}
