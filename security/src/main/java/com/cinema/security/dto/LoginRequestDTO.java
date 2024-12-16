package com.cinema.security.dto;

import com.cinema.security.entity.RoleName;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO{

    private String email;
    private String password;
}

