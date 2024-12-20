package com.cinema.security.service;

import com.cinema.security.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {


    public String extractUsername (String token);
    public String generateToken (User user);
    public boolean isTokenValid (String token, UserDetails userDetails);
    public boolean isTokenExpired (String token);
    public String generateRefreshToken(User user);

}
