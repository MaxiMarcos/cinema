package com.cinema.carrito.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleSeatDTO {

    private String number;
    private double price;
}
