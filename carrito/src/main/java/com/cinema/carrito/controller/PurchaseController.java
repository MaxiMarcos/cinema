package com.cinema.carrito.controller;

import com.cinema.carrito.dto.PurchaseDTO;
import com.cinema.carrito.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller("/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping("/create")
    public PurchaseDTO createPurchase(@RequestBody PurchaseDTO purchaseDTO){

        purchaseService.createPurchase(purchaseDTO);
    }
}
