package com.cinema.theater.service;

import com.cinema.theater.dto.SeatDTO;
import com.cinema.theater.entity.Seat;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SeatService {

    SeatDTO createSeat(SeatDTO seatDTO);
    void createSeats(SeatDTO seatDTO);
    void deleteSeat(Long id);

    SeatDTO getSeat(Long id);

    List<SeatDTO> getAllSeats();

    SeatDTO editSeat(Long id, Seat seat);

    void editStatusSeat(Long id, boolean isAvailable);

    List<SeatDTO> byTheaterId(Long theaterId);
}
