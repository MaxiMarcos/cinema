package com.cinema.theater.mapper;

import com.cinema.theater.dto.ScheduleDTO;
import com.cinema.theater.dto.TheaterDTO;
import com.cinema.theater.entity.Theater;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TheaterMapper {

    public TheaterDTO toTheaterDTO(Theater theater, List<Long> scheduleIds){

        return TheaterDTO.builder()
                .name(theater.getName())
                .capacity(theater.getCapacity())
                .screenType(theater.getScreenType())
                .scheduleIds(scheduleIds)
                .build();
    }

    public Theater toTheaterSet(TheaterDTO theaterDTO, List<ScheduleDTO> scheduleDTOs) {
        // Extraemos los horarios desde los ScheduleDTOs
        List<LocalDateTime> startTimes = scheduleDTOs.stream()
                .map(ScheduleDTO::getStartTime)
                .collect(Collectors.toList());

        return Theater.builder()
                .name(theaterDTO.getName())
                .capacity(theaterDTO.getCapacity())
                .screenType(theaterDTO.getScreenType())
                .startTime(startTimes)
                .build();
    }

}
