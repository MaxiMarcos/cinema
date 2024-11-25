package com.cinema.theater.service;

import com.cinema.theater.dto.SeatDTO;
import com.cinema.theater.entity.Seat;
import org.springframework.http.ResponseEntity;

public interface SeatService {

    void createSeat(SeatDTO seatDTO);

    void deleteSeat(Long id);

    Seat getSeat(Long id);

    void editSeat(Long id, Seat seat);

    void editStatusSeat(Long id, boolean isAvailable);
}
