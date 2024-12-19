package com.cinema.carrito.service.impl;

import com.cinema.carrito.dto.OrderDTO;
import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.enums.Status;
import com.cinema.carrito.repository.PurchaseRepository;
import com.cinema.carrito.repository.SeatClientAPI;
import com.cinema.carrito.service.FinalService;
import com.cinema.carrito.service.OrderService;
import com.cinema.carrito.service.PurchaseService;
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

    @Override
    public void createOrderWithCart(List<Long> movieIds, List<Long> scheduleIds, List<Long> seatIds, OrderDTO orderDTO) {

        PurchaseItem purchaseItem = new PurchaseItem();

        List<Long> updatedSeatIds = new ArrayList<>();

        try {
            // 1. Agregar al carrito
            updatedSeatIds = purchaseService.addToCart(movieIds, scheduleIds, seatIds, purchaseItem);

            purchaseRepo.save(purchaseItem);

            // 3. Crear la orden con el PurchaseItem
            orderDTO.setPurchaseItem(purchaseItem); // Establecer el PurchaseItem en el DTO de la orden
            orderService.createOrder(orderDTO); // Crear la orden

        } catch (Exception e) {
            // Rollback manual: volver a marcar los asientos como disponibles
            for (Long seatId : updatedSeatIds) {
                try {
                    SeatAPI.editStatusSeat(seatId, true);
                } catch (Exception rollbackEx) {
                    System.err.println("Error al revertir el estado del asiento " + seatId + ": " + rollbackEx.getMessage());
                }
            }
            throw new RuntimeException("Error durante la transacción: " + e.getMessage(), e);
        }
    }
}
