package com.cinema.carrito.service.impl;

import com.cinema.carrito.dto.*;
import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.enums.Status;
import com.cinema.carrito.mapper.mapperDTOs;
import com.cinema.carrito.repository.MovieClientAPI;
import com.cinema.carrito.repository.PurchaseRepository;
import com.cinema.carrito.repository.SeatClientAPI;
import com.cinema.carrito.service.PurchaseService;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepo;
    @Autowired
    MovieClientAPI MovieAPI;
    @Autowired
    SeatClientAPI SeatAPI;
    @Autowired
    mapperDTOs mapperDTOs;

    @Transactional
    public PurchaseDTO addToCart(List<Long>scheduleIds, List<Long>seatIds) throws SystemException {

        List<SeatDTO> seats = new ArrayList<>();
        List<ScheduleDTO> schedules = new ArrayList<>();
        List<MovieDTO> movies = new ArrayList<>();
        List<TheaterDTO> theaters = new ArrayList<>();
        PurchaseItem purchaseItem = new PurchaseItem();

        List<Long> updatedSeatIds = new ArrayList<>();
        int price = 0;

        try {
            for (Long scheduleId : scheduleIds) {
                try {
                    ScheduleDTO scheduleDTO = MovieAPI.getSchedule(scheduleId);

                    if (scheduleDTO != null) {
                        schedules.add(scheduleDTO);
                        movies.add(scheduleDTO.getMovie());
                    }

                } catch (Exception e) {

                    System.out.println("Error al obtener un schedule para el id" + scheduleId + ": " + e.getMessage());
                }
            }
            for (Long seatId : seatIds) {

                try {
                    SeatDTO seatDTO = SeatAPI.getSeat(seatId);

                    if (seatDTO != null && seatDTO.getIsAvailable()) {
                        try {
                            price += seatDTO.getPrice();
                            updatedSeatIds.add(seatId);
                            SeatAPI.editStatusSeat(seatId, false);
                            seats.add(SeatAPI.getSeat(seatId));
                            theaters.add(SeatAPI.getTheater(seatDTO.getTheater_id()));
                        } catch (Exception e) {
                            System.out.println("Error al editar el estado del asiento " + seatId + ": " + e.getMessage());
                        }
                    } else {
                        System.out.println("Seat no válido: " + (seatDTO == null ? "SeatDTO es null" : "No disponible"));
                    }

                } catch (Exception e) {
                    System.out.println("Error al obtener el seat para el id " + seatId + ": " + e.getMessage());
                }
            }

            if (validatePurchaseItem(seats, schedules, movies, theaters)) {
                purchaseItem.setMoviee(movies.stream().map(MovieDTO::getName).collect(Collectors.toList()));
                purchaseItem.setTotalPrice(price);
                purchaseItem.setSeat(seats.stream().map(SeatDTO::getNumber).collect(Collectors.toList()));
                purchaseItem.setSchedule(schedules.stream().map(ScheduleDTO::getStartTime).collect(Collectors.toList()));

                purchaseRepo.save(purchaseItem);

            }

            PurchaseDTO purchaseDTO = new PurchaseDTO();
            purchaseDTO.setSeatDTO(seats);
            purchaseDTO.setScheduleDTO(schedules);
            purchaseDTO.setPriceTotal(price);
            purchaseDTO.setMovieDTO(movies);
            purchaseDTO.setUpdatedSeatIds(updatedSeatIds);

            return purchaseDTO;


        }catch(Exception e){
            rollbackSeats(updatedSeatIds);
            throw new SystemException("Error adding to cart: " + e.getMessage());
        }

    }

    public void rollbackSeats(List<Long> updatedSeatIds) {
        if (updatedSeatIds == null || updatedSeatIds.isEmpty()) {
            System.out.println("There are no seats to reverse.");
            return;
        }

        for (Long seatId : updatedSeatIds) {
            try {
                SeatAPI.editStatusSeat(seatId, true);
            } catch (Exception rollbackEx) {
                System.err.println("Error reversing seat status: " + seatId + ": " + rollbackEx.getMessage());
            }
        }
    }
    private boolean validatePurchaseItem(
            List<SeatDTO> seats,
            List<ScheduleDTO> schedules,
            List<MovieDTO> movies,
            List<TheaterDTO> theaters
    ) {
        if (seats == null || seats.isEmpty()) {
            throw new ValidationException("Validation failed: The seat list is null or empty.");
        }
        if (schedules == null || schedules.isEmpty()) {
            throw new ValidationException("Validation failed: The schedules list is null or empty.");
        }
        if (movies == null || movies.isEmpty()) {
            throw new ValidationException("Validation failed: The movie list is null or empty.");
        }


        for (SeatDTO seat : seats) {
            boolean isSeatValid = false;

            for (TheaterDTO theater : theaters) {
                if (seat.getTheater_id().equals(theater.getId())) {
                    isSeatValid = true;
                    break;
                }
            }

            if (!isSeatValid) {
                throw new ValidationException(
                        "Validation failed: Seat number " + seat.getNumber() + " does not belong to any schedule's theater."
                );
            }
        }

        for (ScheduleDTO schedule : schedules) {
            boolean isScheduleValid = false;

            for (MovieDTO movie : movies) {
                if (schedule.getMovie().equals(movie)) {
                    isScheduleValid = true;
                    break;
                }
            }

            if (!isScheduleValid) {
                throw new ValidationException(
                        "Validation failed: Schedule " + schedule.getStartTime() + " is not related to any movie."
                );
            }

        }

        return true;
    }


    @Override
    public PurchaseItem getPurchase(Long id) {
        return purchaseRepo.findById(id).orElse(null);
    }

    @Override
    public List<PurchaseItem> getAllPurchase() {
        return purchaseRepo.findAll();
    }

    @Override
    public void deletePurchase(Long id) {

        purchaseRepo.deleteById(id);
    }

    @Override
    public void editStatusPurchase(Long id, Status COMPLETED) {

        PurchaseItem purchaseItem = purchaseRepo.findById(id).orElse(null);

        purchaseItem.setStatus(COMPLETED);

        purchaseRepo.save(purchaseItem);

    }


}
