package com.cinema.carrito.dto;

import com.cinema.carrito.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDtoAlternativo {

    private Long seatId;
    private Long scheduleId;
    private double totalPrice;
    private Status status;
}
