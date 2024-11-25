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

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepo;

    @Autowired
    MovieClientAPI MovieAPI;

    @Autowired
    SeatClientAPI SeatAPI;

    public void addToCart(List<Long> movieIds, List<Long>scheduleIds, List<Long>SeatIds){

        // creo la lista que luego va a recibir las movies
        List <String> movie = new ArrayList<>();

        int price = 0;

        // busco las movies con movieAPI, las traigo desde el otro microserv y la guardo en un dto
        // desde el dto las envio a la List Movie
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
// Depuración para verificar los valores en 'movie'
        System.out.println("Movies to save: " + movie);



        List<LocalDateTime> schedules = new ArrayList<>();

        for(Long scheduleId : scheduleIds){

            try {
                ScheduleDTO scheduleDTO = MovieAPI.getSchedule(scheduleId);

                if(scheduleDTO != null){

                    schedules.add(scheduleDTO.getStartTime());
                }

            } catch (Exception e){

                System.out.println("Error al obtener un schedule para el id" + scheduleId + ": " + e.getMessage());

            }
        }

        // Depuración para verificar los valores en 'movie'
        System.out.println("Movies to save: " + movie);

        // Depuración para verificar los valores en 'movie'
        System.out.println("Schedules to save: " + schedules);


        List <String> theSeat = new ArrayList<>();


        for(Long seatId : SeatIds) {
            try {
                SeatDTO seatDTO = SeatAPI.getSeat(seatId);

                if(seatDTO != null && seatDTO.getIsAvailable()){

                    price += seatDTO.getPrice();
                    theSeat.add(seatDTO.getFila() + seatDTO.getNumber());

                    SeatAPI.editStatusSeat(seatId, false);

                }else{
                    System.out.println("el asiento ya está ocupado o no existe");
                }
            } catch (Exception e) {
                System.out.println("Error al obtener el seat para el id " + seatId + ": " + e.getMessage());
            }
        }

        //creo un PurchaseItem para asignarle los valores que obtuve mediante dtos (siempre y cuando el asiento esté disponible)


        if(validatePurchaseItem(theSeat, schedules, movie)){
            PurchaseItem purchaseItem = new PurchaseItem();

            purchaseItem.setMoviee(movie);
            purchaseItem.setTotalPrice(price);
            purchaseItem.setSeat(theSeat);
            purchaseItem.setSchedule(schedules);

            purchaseRepo.save(purchaseItem);
        }else{

            System.out.println("La compra no se puede realizar porque está incompleta");
            System.out.println("movie: " + movie);
            System.out.println("theSeat: " + theSeat);
            System.out.println("schedules: " + schedules);
        }


    }


    // Las 3 listas deben tener un valor (validador)
    private boolean validatePurchaseItem(List<String> theSeat, List<LocalDateTime> schedules, List<String> movie) {
        return theSeat != null && !theSeat.isEmpty() &&
                schedules != null && !schedules.isEmpty() &&
                movie != null && !movie.isEmpty();
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
