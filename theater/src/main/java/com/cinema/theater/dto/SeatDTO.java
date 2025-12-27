package com.cinema.theater.dto;

import com.cinema.theater.entity.Theater;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {

    private Long id;
    private int number;
    private double price;
    private Boolean isAvailable;

    private Long theater_id;
}