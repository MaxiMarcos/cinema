package com.cinema.authservice;

import com.cinema.authservice.model.User;
import com.cinema.authservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("testuser").isEmpty()) {
                User user = new User();
                user.setUsername("testuser");
                user.setPassword(passwordEncoder.encode("password"));
                user.setRoles(new HashSet<>(Arrays.asList("ROLE_USER")));
                userRepository.save(user);
                System.out.println("Default user 'testuser' created.");
            }
        };
    }
}
