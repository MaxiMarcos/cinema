package com.cinema.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {

    String name;
    String email;
    String password;
}
