package com.cinema.security.dto;

import com.cinema.security.entity.RoleName;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {


    private String username;
    private String email;
    @Enumerated(EnumType.STRING)
    private RoleName role;
}
