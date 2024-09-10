package com.cinema.theater.service.impl;

import com.cinema.theater.entity.Seat;
import com.cinema.theater.repository.SeatRepository;
import com.cinema.theater.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    SeatRepository seatRepo;

    public void createSeat(Seat seat){

        seatRepo.save(seat);
    }

    public void deleteSeat(Long id){

        seatRepo.deleteById(id);
    }

    public Seat getSeat(Long id){

        return seatRepo.findById(id).orElse(null);
    }
}
