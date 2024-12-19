package com.cinema.carrito.controller;

import com.cinema.carrito.dto.PurchaseDTO;
import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.enums.Status;
import com.cinema.carrito.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @GetMapping("/get/{id}")
    public ResponseEntity getPurchase(@PathVariable Long id){

        PurchaseItem purchaseItem = purchaseService.getPurchase(id);

        if(purchaseItem != null){
            return new ResponseEntity<>(purchaseItem, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Hay error", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllPurchase(){

        List<PurchaseItem> purchaseItem= purchaseService.getAllPurchase();

        if(purchaseItem != null){
            return new ResponseEntity<>(purchaseItem, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Hay error", HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePurchase(@PathVariable Long id){

        PurchaseItem purchaseItem = purchaseService.getPurchase(id);

        if(purchaseItem != null){
            purchaseService.deletePurchase(id);
            return new ResponseEntity<>("purchaseItem eliminado satisfactioriamente", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Hay error", HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/editStatus/status")
    public ResponseEntity editStatusPurchase(@RequestParam Long id, Status COMPLETED){

        PurchaseItem purchaseItem = purchaseService.getPurchase(id);

        if(purchaseItem != null){
            purchaseService.editStatusPurchase(id, COMPLETED);
            return new ResponseEntity<>("purchaseItem editado satisfactioriamente", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Hay error", HttpStatus.NO_CONTENT);
        }
    }

}
