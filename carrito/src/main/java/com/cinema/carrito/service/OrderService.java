package com.cinema.carrito.service;

import com.cinema.carrito.entity.TheOrder;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    void createOrder(TheOrder order);

    TheOrder getOrder(Long id);

    List<TheOrder> getAllOrders();

    void deleteOrder(Long id);
}
