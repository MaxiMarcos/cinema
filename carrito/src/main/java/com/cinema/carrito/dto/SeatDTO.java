package com.cinema.carrito.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class SeatDTO {

    private int number;
    private String fila;
    private double price;
}
