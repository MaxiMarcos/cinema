package com.cinema.security.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    public enum TokenType{
        BEARER
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String token;
    private boolean revoked;
    private boolean expired;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
