package com.cinema.carrito.service;

import com.cinema.carrito.dto.PurchaseDTO;
import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.enums.Status;
import com.cinema.carrito.repository.PurchaseRepository;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PurchaseService {

    PurchaseDTO addToCart (List<Long>scheduleIds, List<Long>SeatIds) throws SystemException;

    PurchaseItem getPurchase(Long id);
    List<PurchaseItem> getAllPurchase();

    void deletePurchase(Long id);

    void editStatusPurchase(Long id, Status status);

    List<Long> findOccupiedSeatIdsByScheduleId(Long scheduleId);

    PurchaseItem createPendingPurchaseItem(Long scheduleId, Long seatId);
}
