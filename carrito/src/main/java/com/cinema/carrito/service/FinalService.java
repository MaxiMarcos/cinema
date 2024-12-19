package com.cinema.carrito.service;

import com.cinema.carrito.dto.OrderDTO;

import java.util.List;

public interface FinalService {

    public void createOrderWithCart(List<Long> movieIds, List<Long> scheduleIds, List<Long> seatIds, OrderDTO orderDTO);
}
