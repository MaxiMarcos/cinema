package com.cinema.security.dto;

import com.cinema.security.entity.RoleName;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleName role = RoleName.CUSTOMER;

}
