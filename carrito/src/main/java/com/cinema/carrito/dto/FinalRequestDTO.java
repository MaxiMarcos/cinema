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
public class FinalRequestDTO {

    public List<Long> movieIds;
    public List<Long> scheduleIds;
    public List<Long> seatIds;
    public OrderDTO orderDTO;

}
