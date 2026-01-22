package com.maximarcos.cinema.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // En este microservicio, asumimos que si el JWT es válido, el usuario es un ADMIN.
        // La autenticación real y la determinación de roles se realiza en el auth-service.
        // Aquí solo construimos un UserDetails con el rol ADMIN para el contexto de seguridad.
        return new User(username, "", Collections.emptyList());
    }
}
