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


    private List<SimpleMovieDTO> movieDTO = new ArrayList<>();;
   // private List<TheaterDTO> theaterDTO = new ArrayList<>();;
    private List<SimpleSeatDTO> seatDTO = new ArrayList<>();;
    private List<SimpleScheduleDTO> scheduleDTO = new ArrayList<>();;
    private double priceTotal;

    @JsonIgnore
    private List<Long> updatedSeatIds = new ArrayList<>();
}
