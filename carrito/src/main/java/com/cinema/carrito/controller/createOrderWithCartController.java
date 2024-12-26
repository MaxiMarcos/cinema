package com.cinema.carrito.controller;

import com.cinema.carrito.dto.FinalRequestDTO;
import com.cinema.carrito.dto.OrderDTO;
import com.cinema.carrito.dto.PurchaseDTO;
import com.cinema.carrito.service.FinalService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/final")
public class createOrderWithCartController {

    @Autowired
    FinalService finalService;

    @PostMapping("/createOrderCart")
    public ResponseEntity<?> createOrderWithCart (@RequestBody FinalRequestDTO finalRequestDTO){

        try{
            PurchaseDTO purchaseDTO = finalService.createOrderWithCart(finalRequestDTO.movieIds, finalRequestDTO.scheduleIds, finalRequestDTO.seatIds, finalRequestDTO.orderDTO);
            return new ResponseEntity<>(purchaseDTO, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
