package com.cinema.carrito.repository;

import com.cinema.carrito.dto.SeatDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="theater-service")
public interface SeatClientAPI {

   @GetMapping("/seat/get/{seatId}")
    public SeatDTO getSeat(@PathVariable("seatId") Long seatId);

}


