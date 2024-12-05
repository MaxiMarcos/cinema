package com.cinema.security.repository;

import com.cinema.security.dto.UserResponseDTO;
import com.cinema.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {

    List<UserResponseDTO> findAllBy();

    Optional<User> findByUsername(String username);
}
