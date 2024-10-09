package com.cinema.carrito.entity;

import com.cinema.carrito.dto.SeatDTO;
import com.cinema.carrito.dto.TicketDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    //private TicketDTO ticket;
    private SeatDTO seatDTO;


    private double totalPrice;
}
