package com.cinema.carrito.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleScheduleDTO {

    private LocalDateTime startTime;
}
