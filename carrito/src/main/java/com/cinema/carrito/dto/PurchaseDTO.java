package com.cinema.carrito.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {

    private List<Long> movieIds = new ArrayList<>();
    private List<Long>scheduleIds = new ArrayList<>();
    private List<Long>SeatIds = new ArrayList<>();


    //private MovieDTO movieDTO;
    //private SeatDTO seatDTO;
    //private ScheduleDTO scheduleDTO;

    private double totalPrice;
}
