package com.cinema.carrito.service.impl;

import com.cinema.carrito.dto.*;
import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.enums.Status;
import com.cinema.carrito.repository.MovieClientAPI;
import com.cinema.carrito.repository.PurchaseRepository;
import com.cinema.carrito.repository.SeatClientAPI;
import com.cinema.carrito.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepo;

    @Autowired
    MovieClientAPI MovieAPI;

    @Autowired
    SeatClientAPI SeatAPI;

    public List<Long> addToCart(List<Long> movieIds, List<Long>scheduleIds, List<Long>SeatIds, PurchaseItem purchaseItem){

        // SOLUCIONAR QUE EL MÉTODO DEJE DE DEVOLVER "OK" SI EN REALIDAD NO SE CONCRETA CORRECTAMENTE


        List <String> movie = new ArrayList<>();
        List<LocalDateTime> schedules = new ArrayList<>();
        List <String> theSeat = new ArrayList<>();
        List<Long> updatedSeatIds = new ArrayList<>();
        int price = 0;


        for(Long movieId : movieIds) {
            try {
                MovieDTO movieDTO = MovieAPI.getMovie(movieId);

                if(movieDTO != null){

                    movie.add(movieDTO.getName());
                }
            } catch (Exception e) {
                System.out.println("Error al obtener la película para el id " + movieId + ": " + e.getMessage());
            }
        }

        System.out.println("Movies to save: " + movie);


        for(Long scheduleId : scheduleIds){

            try {
                ScheduleDTO scheduleDTO = MovieAPI.getSchedule(scheduleId);

                if(scheduleDTO != null){

                    schedules.add(scheduleDTO.getStartTime());
                }

            } catch (Exception e){

                System.out.println("Error al obtener un schedule para el id" + scheduleId + ": " + e.getMessage());
                e.printStackTrace();

            }
        }

        System.out.println("Schedules to save: " + schedules);


        for(Long seatId : SeatIds) {

            try {
                SeatDTO seatDTO = SeatAPI.getSeat(seatId);

                if(seatDTO != null && seatDTO.getIsAvailable()){

                    price += seatDTO.getPrice();
                    theSeat.add(seatDTO.getNumber());
                    SeatAPI.editStatusSeat(seatId, false);
                    updatedSeatIds.add(seatId);
                }

            } catch (Exception e) {
                System.out.println("Error al obtener el seat para el id " + seatId + ": " + e.getMessage());
            }
        }

        // Guardamos la compra en la BD siempre y cuando los procesos anteriores hayan salido bien
        if(validatePurchaseItem(theSeat, schedules, movie)) {
            purchaseItem.setMoviee(movie);
            purchaseItem.setTotalPrice(price);
            purchaseItem.setSeat(theSeat);
            purchaseItem.setSchedule(schedules);

            purchaseRepo.save(purchaseItem);

        }

        return updatedSeatIds;

    }


    // Las 3 listas deben tener un valor guardado para que se efectúe la compra (validador)
    private boolean validatePurchaseItem(List<String> theSeat, List<LocalDateTime> schedules, List<String> movie) {
        if (theSeat == null || theSeat.isEmpty()) {
            statusSeatToTrue(theSeat);
            throw new IllegalArgumentException("Validation failed: The seat list is null or empty.");
        }

        if (schedules == null || schedules.isEmpty()) {
            statusSeatToTrue(theSeat);
            throw new IllegalArgumentException("Validation failed: The schedules list is null or empty.");

        }

            if (movie == null || movie.isEmpty()) {
                statusSeatToTrue(theSeat);
                throw new IllegalArgumentException("Validation failed: The movie list is null or empty.");

            }
        return true;
    }

    private void statusSeatToTrue(List <String> theSeat){
        for (String seatId : theSeat) {
            Long numero = Long.parseLong(seatId);
            SeatDTO seatDTO = SeatAPI.getSeat(numero);
            if (seatDTO != null) {
                SeatAPI.editStatusSeat(numero, true);
            }
        }
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
