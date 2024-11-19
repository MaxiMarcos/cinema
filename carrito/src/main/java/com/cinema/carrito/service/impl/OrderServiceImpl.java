package com.cinema.carrito.service.impl;

import com.cinema.carrito.entity.TheOrder;
import com.cinema.carrito.repository.OrderRepository;
import com.cinema.carrito.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepo;

    public void createOrder(TheOrder order){

        orderRepo.save(order);
    }

    @Override
    public TheOrder getOrder(Long id) {
        return orderRepo.findById(id).orElse(null);
    }

    @Override
    public List<TheOrder> getAllOrders() {
        return orderRepo.findAll();
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepo.deleteById(id);
    }
}
