package com.cinema.segurity.service;

import com.cinema.segurity.dto.RegisterRequestDTO;
import com.cinema.segurity.service.impl.AuthServiceImpl;

public interface AuthService {

    void register(RegisterRequestDTO registerRequestDTO);
}
