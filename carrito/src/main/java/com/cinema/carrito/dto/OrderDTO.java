package com.cinema.carrito.dto;

import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private PurchaseItem purchaseItem;
}
