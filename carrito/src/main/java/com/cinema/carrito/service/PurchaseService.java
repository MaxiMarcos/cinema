package com.cinema.carrito.service;

import com.cinema.carrito.dto.PurchaseDTO;
import com.cinema.carrito.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface PurchaseService {

    void createPurchase(List<Long> movieIds, List<Long>scheduleIds, List<Long>SeatIds, double totalPrice);
}
