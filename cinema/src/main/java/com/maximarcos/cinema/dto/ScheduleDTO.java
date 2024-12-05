package com.maximarcos.cinema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maximarcos.cinema.entity.Movie;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // ignora y no retorna los valores null (me sirve para la función findScheduleByMovie)
public class ScheduleDTO {

    private LocalDateTime startTime;
    private Long movie_id;
}
