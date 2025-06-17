package com.cinema.carrito.mapper;

import com.cinema.carrito.dto.OrderDTO;
import com.cinema.carrito.entity.TheOrder;
import org.springframework.stereotype.Component;

@Component
public class TheOrderMapper {

    public TheOrder newOrderFromDto (OrderDTO orderDTO){
        return TheOrder.builder()
                .purchaseItem(orderDTO.getPurchaseItem())
                .build();
    }

}
