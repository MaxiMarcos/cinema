package com.cinema.carrito.service.impl;

import com.cinema.carrito.dto.*;
import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.enums.Status;
import com.cinema.carrito.mapper.mapperDTOs;
import com.cinema.carrito.repository.MovieClientAPI;
import com.cinema.carrito.repository.PurchaseItemRepository;
import com.cinema.carrito.repository.PurchaseRepository;
import com.cinema.carrito.repository.SeatClientAPI;
import com.cinema.carrito.service.PurchaseService;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.cinema.carrito.exception.ValidationException;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    PurchaseItemRepository purchaseRepo;
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
                if (movies.get(0) == null) {
                    throw new RuntimeException("Validation failed: MovieDTO is null.");
                }
                if (seats.get(0) == null) {
                    throw new RuntimeException("Validation failed: SeatDTO is null.");
                }
                if (schedules.get(0) == null) {
                    throw new RuntimeException("Validation failed: ScheduleDTO is null.");
                }
                purchaseItem.setMovieId(movies.get(0).getId());
                purchaseItem.setTotalPrice(price);
                purchaseItem.setSeatId(seatIds.get(0));
                purchaseItem.setScheduleId(schedules.get(0).getMovie().getId());
                purchaseItem.setTheaterId(schedules.get(0).getTheaterId());

                purchaseRepo.save(purchaseItem);

            }

            PurchaseDTO purchaseDTO = new PurchaseDTO();
            if (seats.get(0) == null) {
                throw new RuntimeException("Validation failed: SeatDTO is null for PurchaseDTO.");
            }
            if (schedules.get(0) == null) {
                throw new RuntimeException("Validation failed: ScheduleDTO is null for PurchaseDTO.");
            }
            if (movies.get(0) == null) {
                throw new RuntimeException("Validation failed: MovieDTO is null for PurchaseDTO.");
            }
            purchaseDTO.setSeat(seats.get(0));
            purchaseDTO.setSchedule(schedules.get(0));
            purchaseDTO.setPrice(price);
            purchaseDTO.setMovie(movies.get(0));
            //purchaseDTO.setUpdatedSeatIds(updatedSeatIds);

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
            throw new RuntimeException("Validation failed: The seat list is null or empty.");
        }
        if (schedules == null || schedules.isEmpty()) {
            throw new RuntimeException("Validation failed: The schedules list is null or empty.");
        }
        if (movies == null || movies.isEmpty()) {
            throw new RuntimeException("Validation failed: The movie list is null or empty.");
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
                throw new RuntimeException(
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
                throw new RuntimeException(
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
    public void editStatusPurchase(Long id, Status status) {

        PurchaseItem purchaseItem = purchaseRepo.findById(id).orElse(null);

        purchaseItem.setStatus(status);

        if (status == Status.CANCELED) {
            try {
                SeatAPI.editStatusSeat(purchaseItem.getSeatId(), true);
                System.out.println("Asiento " + purchaseItem.getSeatId() + " liberado debido a cancelación de reserva.");
            } catch (Exception e) {
                System.err.println("Error al liberar el asiento " + purchaseItem.getSeatId() + ": " + e.getMessage());
            }
        }

        purchaseRepo.save(purchaseItem);

    }

   @Override
    public List<Long> findOccupiedSeatIdsByScheduleId(Long scheduleId) {
        return purchaseRepo.findByScheduleIdAndStatusIn(scheduleId, Arrays.asList(Status.PENDING, Status.COMPLETED))
                .stream()
                .map(PurchaseItem::getSeatId)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseItem createPendingPurchaseItem(Long scheduleId, Long seatId) {
        List<PurchaseItem> existingPurchases = purchaseRepo.findByScheduleIdAndSeatIdAndStatusIn(
                scheduleId, seatId, Arrays.asList(Status.PENDING, Status.COMPLETED));

        if (!existingPurchases.isEmpty()) {
            throw new ValidationException("El asiento " + seatId + " para el horario " + scheduleId + " ya está ocupado o pendiente de compra.");
        }

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setScheduleId(scheduleId);
        purchaseItem.setSeatId(seatId);
        purchaseItem.setStatus(Status.PENDING);

        try {
            // Obtener el precio del asiento
            SeatDTO seatDTO = SeatAPI.getSeat(seatId);
            if (seatDTO != null) {
                purchaseItem.setTotalPrice(seatDTO.getPrice());
            } else {
                System.err.println("SeatDTO es null para seatId: " + seatId);
                // Manejar el caso donde el asiento no se encuentra
            }

            // Obtener movieId y theaterId del schedule
            ScheduleDTO scheduleDTO = MovieAPI.getSchedule(scheduleId);
            if (scheduleDTO != null) {
                if (scheduleDTO.getMovie() != null) {
                    purchaseItem.setMovieId(scheduleDTO.getMovie().getId());
                }
                purchaseItem.setTheaterId(scheduleDTO.getTheaterId());
            } else {
                System.err.println("ScheduleDTO es null para scheduleId: " + scheduleId);
                // Manejar el caso donde el schedule no se encuentra
            }

        } catch (Exception e) {
            System.err.println("Error al obtener detalles para crear PurchaseItem pendiente: " + e.getMessage());
            // Dependiendo de la lógica de negocio, podrías lanzar una excepción o devolver null
        }

        return purchaseRepo.save(purchaseItem);
    }
}
