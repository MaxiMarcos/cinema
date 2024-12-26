package com.cinema.carrito.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheaterDTO {
    private Long id;
    private String name;
    private int capacity;
    private String screenType;
}
