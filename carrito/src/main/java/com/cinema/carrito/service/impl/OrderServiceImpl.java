package com.cinema.carrito.service.impl;

import com.cinema.carrito.entity.TheOrder;
import com.cinema.carrito.repository.OrderRepository;
import com.cinema.carrito.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepo;

    public void createOrder(TheOrder order){

        orderRepo.save(order);
    }
}
