package com.cinema.carrito.controller;

import com.cinema.carrito.dto.OrderDTO;
import com.cinema.carrito.entity.TheOrder;
import com.cinema.carrito.enums.Status;
import com.cinema.carrito.service.OrderService;
import com.cinema.carrito.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/theorder")
public class OrderController {

    @Autowired
    OrderService orderService;

    //Se produce la compra de lo que hay en el carrito
    @PostMapping("/create")
    public ResponseEntity createOrder(@RequestBody OrderDTO orderDTO){

        try {
            orderService.createOrder(orderDTO);
            return new ResponseEntity<>("The order was created correctly", HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>("There was an error creating the schedule: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity getOrder(@PathVariable Long id){

        TheOrder order = orderService.getOrder(id);

        if(order != null){
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Hay error", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllOrder(){

        List<TheOrder> orders = new ArrayList<>();

        if(orders != null){
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Hay error", HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id){

        TheOrder order = orderService.getOrder(id);

        if(order != null){
            orderService.deleteOrder(id);
            return new ResponseEntity<>("Orden eliminada correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Hay error", HttpStatus.NO_CONTENT);
        }
    }

}
