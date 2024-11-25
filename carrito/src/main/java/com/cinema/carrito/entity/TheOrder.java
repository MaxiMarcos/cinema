package com.cinema.carrito.entity;

import com.cinema.carrito.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TheOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchaseItem_id", nullable = false)
    private PurchaseItem purchaseItem;

    //private BigDecimal priceTotal;
    //private int numProducts;

}
