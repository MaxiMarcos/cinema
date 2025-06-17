package com.cinema.carrito.service;

import com.cinema.carrito.dto.OrderDTO;
import com.cinema.carrito.dto.PurchaseDTO;
import com.cinema.carrito.dto.PurchaseDtoAlternativo;
import com.cinema.carrito.entity.PurchaseItem;

import java.util.List;

public interface FinalService {

    public PurchaseItem createOrderWithCart(Long purchaseId);
}
