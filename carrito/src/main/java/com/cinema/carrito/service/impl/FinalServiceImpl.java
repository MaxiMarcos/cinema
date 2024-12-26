package com.cinema.carrito.service.impl;

import com.cinema.carrito.dto.*;
import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.enums.Status;
import com.cinema.carrito.repository.MovieClientAPI;
import com.cinema.carrito.repository.PurchaseRepository;
import com.cinema.carrito.repository.SeatClientAPI;
import com.cinema.carrito.service.FinalService;
import com.cinema.carrito.service.OrderService;
import com.cinema.carrito.service.PurchaseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FinalServiceImpl implements FinalService {

    @Autowired
    OrderService orderService;
    @Autowired
    PurchaseService purchaseService;

    @Autowired
    PurchaseRepository purchaseRepo;

    @Autowired
    SeatClientAPI SeatAPI;

    @Autowired
    MovieClientAPI MovieAPI;

    @Override
    public PurchaseDTO createOrderWithCart(List<Long> movieIds, List<Long> scheduleIds, List<Long> seatIds, OrderDTO orderDTO) {


        PurchaseDTO purchaseDTO = new PurchaseDTO();
        PurchaseItem purchaseItem = new PurchaseItem();
        List<Long> updatedSeatIds;

        try {

            purchaseDTO = purchaseService.addToCart(movieIds, scheduleIds, seatIds, purchaseItem);

            orderDTO.setPurchaseItem(purchaseItem);

            orderService.createOrder(orderDTO);

            return purchaseDTO;

        } catch (Exception e) {
            updatedSeatIds = purchaseDTO.getUpdatedSeatIds();
            rollbackSeats(updatedSeatIds);

            throw new RuntimeException("Error in transaction: " + e.getMessage(), e);

        }

    }

    public void rollbackSeats(List<Long> updatedSeatIds) {

        for (Long seatId : updatedSeatIds) {

            try {

                SeatAPI.editStatusSeat(seatId, true);

            } catch (Exception rollbackEx) {

                System.err.println("Error al revertir el estado del asiento " + seatId + ": " + rollbackEx.getMessage());

            }
        }
    }
}


