package com.cinema.theater.service.impl;

import com.cinema.theater.dto.SeatDTO;
import com.cinema.theater.entity.Seat;
import com.cinema.theater.entity.Theater;
import com.cinema.theater.mapper.SeatMapper;
import com.cinema.theater.repository.SeatRepository;
import com.cinema.theater.service.SeatService;
import com.cinema.theater.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    SeatRepository seatRepo;

    @Autowired
    TheaterService theaterService;

    @Autowired
    SeatMapper seatMapper;

    @Override
    public SeatDTO createSeat(SeatDTO seatDTO){

        Seat seat = seatMapper.toSeatSet(seatDTO);
        seatRepo.save(seat);

        return seatMapper.toSeatDTO(seat);
    }

    @Override
    public void deleteSeat(Long id){

        seatRepo.deleteById(id);
    }

    @Override
    public SeatDTO getSeat(Long id){

        Seat seat = seatRepo.findById(id).orElse(null);
        SeatDTO seatDTO = seatMapper.toSeatDTO(seat);

        return seatDTO;
    }

    @Override
    public List<SeatDTO> getAllSeats() {

        List<SeatDTO> listSeat = seatMapper.convertToListDto(seatRepo.findAll());

        return listSeat;
    }

    @Override
    public SeatDTO editSeat(Long id, Seat seat){

        Seat newSeat = seatRepo.findById(id).orElse(null);
        seatRepo.save(newSeat);

        return seatMapper.toSeatDTO(newSeat);
    }

    @Override
    public void editStatusSeat(Long id, boolean isAvailable) {

        Seat newSeat = seatRepo.findById(id).orElse(null);

        newSeat.setIsAvailable(isAvailable);
        seatRepo.save(newSeat);
    }
}
