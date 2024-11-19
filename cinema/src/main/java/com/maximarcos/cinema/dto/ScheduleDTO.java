package com.maximarcos.cinema.dto;

import com.maximarcos.cinema.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {

    private LocalDateTime startTime;
    private Long movie_id;
}
