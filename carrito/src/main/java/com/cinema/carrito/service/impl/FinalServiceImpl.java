package com.cinema.carrito.service.impl;

import com.cinema.carrito.dto.*;
import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.entity.TheOrder;
import com.cinema.carrito.enums.Status;
import com.cinema.carrito.mapper.PurchaseMapper;
import com.cinema.carrito.repository.MovieClientAPI;
import com.cinema.carrito.repository.OrderRepository;
import com.cinema.carrito.repository.PurchaseRepository;
import com.cinema.carrito.repository.SeatClientAPI;
import com.cinema.carrito.service.FinalService;
import com.cinema.carrito.service.OrderService;
import com.cinema.carrito.service.PurchaseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FinalServiceImpl implements FinalService {

    private final OrderRepository orderRepo;
    private final PurchaseRepository purchaseRepo;
    private final SeatClientAPI SeatAPI;
    private final MovieClientAPI MovieAPI;

    @Override
    public PurchaseItem createOrderWithCart(Long purchaseId){

        try {
            PurchaseItem purchaseItem = purchaseRepo.findById(purchaseId).orElse(null);
            purchaseItem.setStatus(Status.COMPLETED);
            purchaseRepo.save(purchaseItem);


            TheOrder order = new TheOrder();
            order.setPurchaseItem(purchaseItem);
            orderRepo.save(order);

            return purchaseItem;

        } catch (Exception e) {
            throw new RuntimeException("Error in transaction: " + e.getMessage(), e);
        }

    }

}


