package com.cinema.carrito.repository;

import com.cinema.carrito.dto.SeatDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="theater-service")
public interface SeatClientAPI {

   @GetMapping("/seat/get/{seatId}")
    public SeatDTO getSeat(@PathVariable("seatId") Long seatId);

   @PutMapping("seat/edit/{id}/status")
    public ResponseEntity editStatusSeat(@PathVariable Long id, @RequestParam Boolean isAvailable);

}


