package com.cinema.authservice.controller;

import com.cinema.authservice.dto.AuthRequest;
import com.cinema.authservice.dto.AuthResponse;
import com.cinema.authservice.jwt.JwtUtil;
import com.cinema.authservice.model.User;
import com.cinema.authservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://127.0.0.1:5501"})
public class AuthServiceController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceController(AuthenticationManager authenticationManager,
                                 UserDetailsService userDetailsService,
                                 JwtUtil jwtUtil,
                                 UserRepository userRepository,
                                 PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> registerUser(@RequestBody AuthRequest authRequest) {
        if (userRepository.findByUsername(authRequest.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        Set<String> roles = new HashSet<>();
        roles.add("USER"); // Default role for new users
        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
