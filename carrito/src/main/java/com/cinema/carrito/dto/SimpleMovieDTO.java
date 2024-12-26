package com.cinema.carrito.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleMovieDTO {
    private String name;
    private String language;
    private String subtitle;
}
