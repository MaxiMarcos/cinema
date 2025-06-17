package com.cinema.carrito.controller;

import com.cinema.carrito.dto.FinalRequestDTO;
import com.cinema.carrito.dto.OrderDTO;
import com.cinema.carrito.dto.PurchaseDTO;
import com.cinema.carrito.dto.PurchaseDtoAlternativo;
import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.service.FinalService;
import com.cinema.carrito.service.impl.FinalServiceImpl;
import com.cinema.carrito.service.impl.PurchaseServiceImpl;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/final")
public class createOrderWithCartController {

    @Autowired
    FinalServiceImpl finalService;
    @Autowired
    PurchaseServiceImpl purchaseService;

    @PostMapping("/order/{purchaseId}")
    public ResponseEntity<?> createOrderWithCart (@PathVariable Long purchaseId){

        try{
            PurchaseItem purchaseItem = finalService.createOrderWithCart(purchaseId);
            return new ResponseEntity<>(purchaseItem, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/cart/")
    public ResponseEntity<?> addToCart (List<Long>scheduleIds, List<Long> seatId){

        try{
            PurchaseDTO purchaseDTO = purchaseService.addToCart(scheduleIds, seatId);
            return new ResponseEntity<>(purchaseDTO, HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
