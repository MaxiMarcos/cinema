package com.cinema.theater.mapper;


import com.cinema.theater.dto.SeatDTO;
import com.cinema.theater.dto.TheaterDTO;
import com.cinema.theater.entity.Seat;
import com.cinema.theater.entity.Theater;
import com.cinema.theater.repository.TheaterRepository;
import com.cinema.theater.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class SeatMapper {

    @Autowired
    TheaterRepository theaterRepo;

    public Seat toSeatSet(SeatDTO seatDTO) {

        Theater theater = theaterRepo.findById(seatDTO.getTheater_id())
                .orElseThrow(() -> new RuntimeException("Theater not found with ID: " + seatDTO.getTheater_id()));

        return Seat.builder()
                .number(seatDTO.getNumber())
                .price(seatDTO.getPrice())
                .isAvailable(seatDTO.getIsAvailable())
                .theater(theater)
                .build();
    }

    public SeatDTO toSeatDTO(Seat seat) {

        return SeatDTO.builder()
                .number(seat.getNumber())
                .price(seat.getPrice())
                .isAvailable(seat.getIsAvailable())
                .theater_id(seat.getTheater().getId())
                .build();
    }


    public List<SeatDTO> convertToListDto(List<Seat> seats){

        if ( seats == null ) {
            return null;
        }

        List<SeatDTO> list = new ArrayList<>();
        for ( Seat c : seats ) {
            list.add(toSeatDTO(c));
        }

        return list;
    }
}

