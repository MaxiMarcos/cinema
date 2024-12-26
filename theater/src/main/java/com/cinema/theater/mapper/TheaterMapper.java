package com.cinema.theater.mapper;

import com.cinema.theater.dto.ScheduleDTO;
import com.cinema.theater.dto.SeatDTO;
import com.cinema.theater.dto.TheaterDTO;
import com.cinema.theater.entity.Seat;
import com.cinema.theater.entity.Theater;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TheaterMapper {

    public TheaterDTO toTheaterDTO(Theater theater, List<Long> scheduleIds) {
        return TheaterDTO.builder()
                .id(theater.getId())
                .name(theater.getName())
                .capacity(theater.getCapacity())
                .screenType(theater.getScreenType())
                .scheduleIds(scheduleIds)
                .build();
    }

    // Sobrecarga para cuando no hay scheduleIds
    public TheaterDTO toTheaterDTOSinSchedule(Theater theater) {

        return toTheaterDTO(theater, null);
    }


    public Theater toTheaterSet(TheaterDTO theaterDTO, List<ScheduleDTO> scheduleDTOs) {
        // Si scheduleDTOs es null, inicializamos una lista vacía
        List<LocalDateTime> startTimes = (scheduleDTOs != null)
                ? scheduleDTOs.stream()
                .map(ScheduleDTO::getStartTime)
                .collect(Collectors.toList())
                : Collections.emptyList(); // Lista vacía si no hay horarios

        return Theater.builder()
                .name(theaterDTO.getName())
                .capacity(theaterDTO.getCapacity())
                .screenType(theaterDTO.getScreenType())
                .startTime(startTimes)
                .build();
    }
    public Theater toTheaterSetSinStartTime(TheaterDTO theaterDTO) {
        return toTheaterSet(theaterDTO, null);
    }

    public List<TheaterDTO> convertToListDto(List<Theater> theaters){

        if ( theaters == null ) {
            return null;
        }

        List<TheaterDTO> list = new ArrayList<>();
        for ( Theater c : theaters ) {
            list.add(toTheaterDTOSinSchedule(c));
        }

        return list;
    }
}
