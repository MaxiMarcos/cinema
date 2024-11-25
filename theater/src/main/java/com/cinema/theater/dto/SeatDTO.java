package com.cinema.theater.dto;

import com.cinema.theater.entity.Theater;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {

    private int number;
    private String fila;
    private double price;
    private Boolean isAvailable;

    private Long theater_id;
}