package com.cinema.carrito.service.impl;

import com.cinema.carrito.dto.OrderDTO;
import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.entity.TheOrder;
import com.cinema.carrito.enums.Status;
import com.cinema.carrito.repository.OrderRepository;
import com.cinema.carrito.repository.PurchaseRepository;
import com.cinema.carrito.service.OrderService;
import com.cinema.carrito.service.PurchaseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    PurchaseRepository purchaseRepo;

    @Autowired
    OrderRepository orderRepo;

    @Override
    public void createOrder(OrderDTO orderDTO) {
        TheOrder theOrder = new TheOrder();
        theOrder.setPurchaseItem(orderDTO.getPurchaseItem());
        orderRepo.save(theOrder);

        PurchaseItem purchaseItem = purchaseService.getPurchase(orderDTO.getPurchaseItem().getId());
        if (purchaseItem == null) {
            throw new EntityNotFoundException("PurchaseItem not found");
        }

        purchaseItem.setStatus(Status.COMPLETED);

        purchaseRepo.save(purchaseItem);
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
