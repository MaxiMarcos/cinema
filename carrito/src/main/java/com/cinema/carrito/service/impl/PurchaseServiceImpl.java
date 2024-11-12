package com.cinema.carrito.service.impl;

import com.cinema.carrito.dto.*;
import com.cinema.carrito.entity.PurchaseItem;
import com.cinema.carrito.repository.MovieClientAPI;
import com.cinema.carrito.repository.PurchaseRepository;
import com.cinema.carrito.repository.ScheduleClientAPI;
import com.cinema.carrito.repository.SeatClientAPI;
import com.cinema.carrito.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepo;

    @Autowired
    MovieClientAPI MovieAPI;

    //@Autowired
   // ScheduleClientAPI ScheduleAPI;

    @Autowired
    SeatClientAPI SeatAPI;

    public void createPurchase(List<Long> movieIds, List<Long>scheduleIds, List<Long>SeatIds){

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


        for(Long seatId : SeatIds) {
            try {
                SeatDTO seatDTO = SeatAPI.getSeat(seatId);

                if(seatDTO != null){

                    price += seatDTO.getPrice();
                }
            } catch (Exception e) {
                System.out.println("Error al obtener la película para el id " + seatId + ": " + e.getMessage());
            }
        }


        // ScheduleDTO scheduleDTO = ScheduleAPI.getSchedule(scheduleId);
        //SeatDTO seatDTO = SeatAPI.getSeat(seatId);


        //creo un PurchaseItem para asignarle los valores que obtuve mediante dtos
        PurchaseItem purchaseItem = new PurchaseItem();

        purchaseItem.setMoviee(movie);
        purchaseItem.setTotalPrice(price);

        purchaseRepo.save(purchaseItem);
    }
}
