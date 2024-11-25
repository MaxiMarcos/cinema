package com.cinema.carrito.dto;

import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private PurchaseItem purchaseItem;
    //private BigDecimal priceTotal;
    //private int numProducts;

}
