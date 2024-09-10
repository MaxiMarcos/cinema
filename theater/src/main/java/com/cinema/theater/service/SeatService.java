package com.cinema.theater.service;

import com.cinema.theater.entity.Seat;

public interface SeatService {

    void createSeat(Seat seat);

    void deleteSeat(Long id);

    Seat getSeat(Long id);
}
