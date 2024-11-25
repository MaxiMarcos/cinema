package com.cinema.carrito.entity;

import com.cinema.carrito.converter.StringListConverter;
import com.cinema.carrito.converter.TimeListConverter;
import com.cinema.carrito.enums.Status;
import jakarta.persistence.*;
import lombok.*;

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

    @Convert(converter = StringListConverter.class) // recibo un string, quizas sea mejor
                                                    // recibir un objeto Movie por relación de entidades

    private List<String> moviee = new ArrayList<>();

    @Convert(converter = StringListConverter.class)// recibo un string, quizas sea mejor
                                                   // recibir un objeto Movie por relación de entidades
    private List<String> seat = new ArrayList<>();

    @Convert(converter = TimeListConverter.class)
    private List<LocalDateTime> schedule = new ArrayList<>();

    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;
}
