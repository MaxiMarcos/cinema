package com.cinema.theater.controller;

import com.cinema.theater.dto.SeatDTO;
import com.cinema.theater.entity.Seat;
import com.cinema.theater.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    SeatService seatService;

    @PostMapping("/create")
    public void createSeat(@RequestBody SeatDTO seatDTO){

        seatService.createSeat(seatDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSeat(@PathVariable Long id){

        seatService.deleteSeat(id);
    }

    @GetMapping("/get/{id}")
    Seat getSeat(@PathVariable Long id){
        return seatService.getSeat(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity editSeat(@PathVariable Long id,
                            @RequestBody Seat seat){

        if (seat != null) {
            seatService.editSeat(id, seat);
            return new ResponseEntity<>(seat, HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Hay un error", HttpStatus.NO_CONTENT);
        }
    }
}
