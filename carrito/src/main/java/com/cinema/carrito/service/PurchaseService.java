package com.cinema.carrito.service;

import com.cinema.carrito.dto.PurchaseDTO;
import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.enums.Status;
import com.cinema.carrito.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PurchaseService {

    List<Long> addToCart(List<Long> movieIds, List<Long>scheduleIds, List<Long>SeatIds, PurchaseItem purchaseItem);

    PurchaseItem getPurchase(Long id);
    List<PurchaseItem> getAllPurchase();

    void deletePurchase(Long id);

    void editStatusPurchase(Long id, Status COMPLETED);
}
