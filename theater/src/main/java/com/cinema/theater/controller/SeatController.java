package com.cinema.theater.controller;

import com.cinema.theater.dto.SeatDTO;
import com.cinema.theater.entity.Seat;
import com.cinema.theater.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seat")
public class SeatController {

    @Autowired
    SeatService seatService;

    @PostMapping("/create")
    public ResponseEntity<String> createSeat(@RequestBody SeatDTO seatDTO){

        try {
            seatService.createSeat(seatDTO);
            return new ResponseEntity<>("The seat was created", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("The seat was not created" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSeat(@PathVariable Long id){

        try {
            seatService.deleteSeat(id);
            return new ResponseEntity<>("The seat was deleted", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("The seat was not deleted" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SeatDTO> getSeat(@PathVariable Long id){

        try{
            SeatDTO seatDTO = seatService.getSeat(id);
            return new ResponseEntity<>(seatDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<SeatDTO>> getAllSeats(){

        try{
            List<SeatDTO> seatDTO= seatService.getAllSeats();
            return new ResponseEntity<>(seatDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editSeat(@PathVariable Long id,
                            @RequestBody Seat seat){

        try {
            seatService.editSeat(id, seat);
            return new ResponseEntity<>("Seat was edited", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Seat was not edited", HttpStatus.BAD_REQUEST);
        }
    }

    // Para editar status
    @PutMapping("/edit/{id}/status")
    public ResponseEntity editStatusSeat(@PathVariable Long id, @RequestParam Boolean isAvailable){

        seatService.editStatusSeat(id, isAvailable);
        return ResponseEntity.ok().build();
    }
}
