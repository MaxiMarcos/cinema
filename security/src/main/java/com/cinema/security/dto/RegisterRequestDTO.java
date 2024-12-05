package com.cinema.security.dto;

import com.cinema.security.entity.RoleName;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    private String username;
    private String email;
    private String password;
   // private RoleName role;

}
