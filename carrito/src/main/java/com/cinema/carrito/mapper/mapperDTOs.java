package com.cinema.carrito.mapper;

import com.cinema.carrito.dto.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class mapperDTOs {

    public SimpleSeatDTO toNewSeatDTO(SeatDTO seatDTO){

        return SimpleSeatDTO.builder()
                .number(seatDTO.getNumber())
                .price(seatDTO.getPrice())
                .build();
    }

    public SimpleScheduleDTO toNewScheduleDTO(ScheduleDTO scheduleDTO){

        return SimpleScheduleDTO.builder()
                .startTime(scheduleDTO.getStartTime())
                .build();
    }

    public SimpleMovieDTO toNewMovieDTO(MovieDTO movieDTO){

        return SimpleMovieDTO.builder()
                .name(movieDTO.getName())
                .language(movieDTO.getLanguage())
                .subtitle(movieDTO.getSubtitle())
                .build();
    }


    public List<SimpleSeatDTO> toListSeatDTO(List<SeatDTO> seatList) {

        if (seatList == null) {
            System.out.println("LIST NULL");
        }

        List<SimpleSeatDTO> newList = new ArrayList<>();
        for (SeatDTO s : seatList) {
            newList.add(toNewSeatDTO(s));
        }
        return newList;
    }

    public List<SimpleScheduleDTO> toListScheduleDTO(List<ScheduleDTO> scheduleList) {

        if (scheduleList == null) {
            System.out.println("LIST NULL");
        }

        List<SimpleScheduleDTO> newList = new ArrayList<>();
        for (ScheduleDTO s : scheduleList) {
            newList.add(toNewScheduleDTO(s));
        }
        return newList;
    }

    public List<SimpleMovieDTO> toListMovieDTO(List<MovieDTO> movieList) {

        if (movieList == null) {
            System.out.println("LIST NULL");
        }

        List<SimpleMovieDTO> newList = new ArrayList<>();
        for (MovieDTO m : movieList) {
            newList.add(toNewMovieDTO(m));
        }
        return newList;

    }
}
