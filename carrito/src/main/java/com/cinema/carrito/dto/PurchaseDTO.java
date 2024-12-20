package com.cinema.carrito.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {


    private MovieDTO movieDTO;
    private List<SeatDTO> seatDTO;
    private List<ScheduleDTO> scheduleDTO;

}
