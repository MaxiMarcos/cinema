package com.cinema.theater.service.impl;
import com.cinema.theater.dto.SeatDTO;
import com.cinema.theater.entity.Seat;
import com.cinema.theater.mapper.SeatMapper;
import com.cinema.theater.repository.SeatRepository;
import com.cinema.theater.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    SeatRepository seatRepo;

    @Autowired
    SeatMapper seatMapper;

    @Override
    public SeatDTO createSeat(SeatDTO seatDTO){

        Seat seat = seatMapper.toSeatSet(seatDTO);
        seatRepo.save(seat);

        return seatDTO;
    }

    public void createSeats(SeatDTO seatDTO){

        boolean asientos[][] = new boolean[10][10];
        int number = 0;

        for(int f = 0; f < 10; f++){
            for(int c = 0; c <10; c++){
                asientos[f][c] = true;
                number++;

                seatDTO.setNumber(number);
                seatDTO.setIsAvailable(asientos[f][c]);
                Seat seat = seatMapper.toSeatSet(seatDTO);
                seatRepo.save(seat);
            }
        }
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

        Seat existingSeat = seatRepo.findById(id).orElse(null);
        if (existingSeat != null) {
            existingSeat.setNumber(seat.getNumber());
            existingSeat.setPrice(seat.getPrice());
            existingSeat.setIsAvailable(seat.getIsAvailable());
            existingSeat.setTheater(seat.getTheater());
            seatRepo.save(existingSeat);
            return seatMapper.toSeatDTO(existingSeat);
        }
        return null; // O lanzar una excepción si el asiento no se encuentra
    }

    //Para usar en el microservicio de Purchase y manejar la disponibilidad según se concrete o no la compra
    @Override
    public void editStatusSeat(Long id, boolean isAvailable) {

        Seat newSeat = seatRepo.findById(id).orElse(null);

        newSeat.setIsAvailable(isAvailable);
        seatRepo.save(newSeat);

        System.out.println("llamando al método del service theater:" + newSeat.getIsAvailable());
    }

    @Override
    public List<SeatDTO> byTheaterId(Long theaterId) {
        List<Seat> seats = seatRepo.findByTheaterId(theaterId);
        return seatMapper.convertToListDto(seats);
    }
}
