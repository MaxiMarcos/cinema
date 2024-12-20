package com.cinema.carrito.service;

import com.cinema.carrito.dto.OrderDTO;
import com.cinema.carrito.dto.PurchaseDTO;

import java.util.List;

public interface FinalService {

    public PurchaseDTO createOrderWithCart(List<Long> movieIds, List<Long> scheduleIds, List<Long> seatIds, OrderDTO orderDTO);
}
