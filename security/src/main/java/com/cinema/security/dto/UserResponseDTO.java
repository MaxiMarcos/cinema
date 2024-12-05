package com.cinema.security.dto;

import com.cinema.security.entity.RoleName;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {


    private String username;
    private String email;
    private RoleName role;
}
