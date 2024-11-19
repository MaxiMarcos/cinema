package com.cinema.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String jwt;
    private UserResponseDTO user;
}
