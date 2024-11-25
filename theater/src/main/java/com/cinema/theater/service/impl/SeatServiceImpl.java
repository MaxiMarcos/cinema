package com.cinema.theater.service.impl;

import com.cinema.theater.dto.SeatDTO;
import com.cinema.theater.entity.Seat;
import com.cinema.theater.entity.Theater;
import com.cinema.theater.repository.SeatRepository;
import com.cinema.theater.service.SeatService;
import com.cinema.theater.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    SeatRepository seatRepo;

    @Autowired
    TheaterService theaterService;

    @Override
    public void createSeat(SeatDTO seatDTO){



        Theater theater = theaterService.getTheater(seatDTO.getTheater_id());
        Seat seat = new Seat();

        seat.setIsAvailable(seatDTO.getIsAvailable());
        seat.setTheater(theater);
        seat.setFila(seatDTO.getFila());
        seat.setNumber(seatDTO.getNumber());
        seat.setPrice(seatDTO.getPrice());

        seatRepo.save(seat);
    }

    @Override
    public void deleteSeat(Long id){

        seatRepo.deleteById(id);
    }

    @Override
    public Seat getSeat(Long id){

        return seatRepo.findById(id).orElse(null);
    }

    @Override
    public void editSeat(Long id, Seat seat){

        Seat newSeat = seatRepo.findById(id).orElse(null);

        newSeat.setFila(seat.getFila());
        newSeat.setNumber(seat.getNumber());
        newSeat.setIsAvailable(seat.getIsAvailable());
        newSeat.setPrice(seat.getPrice());
        newSeat.setTheater(seat.getTheater());

        seatRepo.save(newSeat);
    }

    @Override
    public void editStatusSeat(Long id, boolean isAvailable) {

        Seat newSeat = seatRepo.findById(id).orElse(null);

        newSeat.setIsAvailable(isAvailable);
        seatRepo.save(newSeat);
    }
}
