package com.cinema.carrito.controller;

import com.cinema.carrito.dto.FinalRequestDTO;
import com.cinema.carrito.dto.OrderDTO;
import com.cinema.carrito.service.FinalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/final")
public class createOrderWithCartController {

    @Autowired
    FinalService finalService;

    @PostMapping("/createOrderCart")
    public ResponseEntity<String> createOrderWithCart (@RequestBody FinalRequestDTO finalRequestDTO){

        try{
            finalService.createOrderWithCart(finalRequestDTO.movieIds, finalRequestDTO.scheduleIds, finalRequestDTO.seatIds,finalRequestDTO.orderDTO);
            return new ResponseEntity<>("Todo bien", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Todo mal", HttpStatus.BAD_REQUEST);
        }

    }
}
