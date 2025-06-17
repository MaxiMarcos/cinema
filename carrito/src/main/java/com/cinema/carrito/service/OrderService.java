package com.cinema.carrito.service;

import com.cinema.carrito.dto.OrderDTO;
import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.entity.TheOrder;
import com.cinema.carrito.enums.Status;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    TheOrder getOrder(Long id);

    List<TheOrder> getAllOrders();

    void deleteOrder(Long id);
}
