package com.cinema.carrito.entity;

import com.cinema.carrito.dto.TicketDTO;
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
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private TicketDTO ticket; // aca traigo el producto (ticket == seat+movie+schedule)

    private BigDecimal priceTotal;
    private int numProducts;

    @Enumerated(EnumType.STRING)
    private Status status;
}
