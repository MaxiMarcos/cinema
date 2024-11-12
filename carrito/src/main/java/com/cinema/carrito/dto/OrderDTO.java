package com.cinema.carrito.dto;

import com.cinema.carrito.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

// private TicketDTO ticket; // aca traigo el producto (ticket == seat+movie+schedule)

    private BigDecimal priceTotal;
    private int numProducts;

    @Enumerated(EnumType.STRING)
    private Status status;

}
