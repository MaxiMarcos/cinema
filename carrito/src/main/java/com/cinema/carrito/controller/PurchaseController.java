package com.cinema.carrito.controller;

import com.cinema.carrito.dto.PurchaseDTO;
import com.cinema.carrito.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping("/create")
    public ResponseEntity createPurchase(@RequestBody PurchaseDTO purchaseDTO){

        purchaseService.createPurchase(purchaseDTO.getMovieIds(), purchaseDTO.getScheduleIds(), purchaseDTO.getSeatIds());


        if(purchaseDTO != null){
            return new ResponseEntity<>(purchaseDTO, HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Hay un error", HttpStatus.NO_CONTENT);
        }



    }

}
