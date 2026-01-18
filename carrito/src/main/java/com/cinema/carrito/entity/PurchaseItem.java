package com.cinema.carrito.entity;

import com.cinema.carrito.converter.StringListConverter;
import com.cinema.carrito.converter.TimeListConverter;
import com.cinema.carrito.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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

    private Long movieId;
    private Long scheduleId;
    private Long theaterId;
    private Long seatId;

    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(unique = true, nullable = false, updatable = false)
    private String reservationCode;
}
