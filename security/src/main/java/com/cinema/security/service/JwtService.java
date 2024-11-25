package com.cinema.security.service;

import com.cinema.security.entity.User;

public interface JwtService {

    public String generateToken (User user);

    public String generateRefreshToken(User user);
}
