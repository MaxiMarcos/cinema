package com.cinema.carrito.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDTO {

        private Long id;
        private MovieDTO movie;
        private ScheduleDTO schedule;
        private TheaterDTO theater;
        private SeatDTO seat;
        private double price;
        private String reservationCode;
}
