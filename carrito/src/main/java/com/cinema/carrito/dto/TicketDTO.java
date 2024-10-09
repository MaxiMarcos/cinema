package com.cinema.carrito.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

public class TicketDTO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private SeatDTO SeatDTO;

    private ScheduleDTO scheduleDTO; // trae movie tmb


}
