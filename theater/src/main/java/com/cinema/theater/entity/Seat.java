package com.cinema.theater.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int number;
    private double price;
    private Boolean isAvailable;;
    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;
}
