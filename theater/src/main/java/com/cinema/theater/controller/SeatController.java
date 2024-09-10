package com.cinema.theater.controller;

import com.cinema.theater.entity.Seat;
import com.cinema.theater.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    SeatService seatService;

    @PostMapping("/create")
    public void createSeat(@RequestBody Seat seat){

        seatService.createSeat(seat);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSeat(@PathVariable Long id){

        seatService.deleteSeat(id);
    }

    @GetMapping("/get/{id}")
    Seat getWSeat(@PathVariable Long id){
        return seatService.getSeat(id);
    }
}
