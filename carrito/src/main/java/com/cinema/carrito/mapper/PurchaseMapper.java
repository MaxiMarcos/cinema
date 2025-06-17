package com.cinema.carrito.mapper;

import com.cinema.carrito.dto.PurchaseDTO;
import com.cinema.carrito.dto.PurchaseDtoAlternativo;
import com.cinema.carrito.entity.PurchaseItem;
import org.springframework.stereotype.Component;

@Component
public class PurchaseMapper {

    public PurchaseDtoAlternativo newDtoFromPurchase(PurchaseItem purchase){

        return PurchaseDtoAlternativo.builder()
                .seat(purchase.getSeat())
                .schedule(purchase.getSchedule())
                .totalPrice(purchase.getTotalPrice())
                .status(purchase.getStatus())
                .build();
    }
}
