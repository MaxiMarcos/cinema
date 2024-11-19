package com.cinema.carrito.service;

import com.cinema.carrito.dto.PurchaseDTO;
import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface PurchaseService {

    void addToCart(List<Long> movieIds, List<Long>scheduleIds, List<Long>SeatIds);

    PurchaseItem getPurchase(Long id);
    List<PurchaseItem> getAllPurchase();

    void deletePurchase(Long id);
}
