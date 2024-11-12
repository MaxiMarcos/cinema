package com.cinema.carrito.entity;

import com.cinema.carrito.dto.MovieDTO;
import com.cinema.carrito.dto.ScheduleDTO;
import com.cinema.carrito.dto.SeatDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PurchaseItem {

    // Será la compra de un producto. Varios "purchaseItem" pueden integrar una "Order"

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = StringListConverter.class)
    private List<String> moviee = new ArrayList<>();

    //private SeatDTO seatDTO;
    //private ScheduleDTO scheduleDTO;

    private double totalPrice;
}
