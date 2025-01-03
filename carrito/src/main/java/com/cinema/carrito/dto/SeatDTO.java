package com.cinema.carrito.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatDTO {

    private String number;
    private double price;
    private Boolean isAvailable;
    private Long theater_id;
}
