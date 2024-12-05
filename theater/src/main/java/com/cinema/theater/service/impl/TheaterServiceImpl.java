
package com.cinema.theater.service.impl;

import com.cinema.theater.dto.ScheduleDTO;
import com.cinema.theater.dto.TheaterDTO;
import com.cinema.theater.entity.Seat;
import com.cinema.theater.mapper.SeatMapper;
import com.cinema.theater.mapper.TheaterMapper;
import com.cinema.theater.service.TheaterService;
import com.cinema.theater.entity.Theater;
import com.cinema.theater.repository.ScheduleAPI;
import com.cinema.theater.repository.TheaterRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheaterServiceImpl implements TheaterService {
    @Autowired
    TheaterRepository theaterRepo;

    @Autowired
    private ScheduleAPI scheduleAPI;

    @Autowired
    TheaterMapper theaterMapper;


    @Override
    public List<Theater> getAllTheater(){

        return theaterRepo.findAll();
    }

    @Override
    public Theater getTheater(Long id){

        return theaterRepo.findById(id).orElse(null);
    }

    @Override
    @CircuitBreaker(name="cinema", fallbackMethod = "fallbackCreateTheaterWithSchedule" )
    @Retry(name="cinema")
    public void createTheater(TheaterDTO theaterDTO){

        List <LocalDateTime> startTime = new ArrayList<>();
        List<ScheduleDTO> scheduleDTO = new ArrayList<>();

         for (Long scheduleId : theaterDTO.getScheduleIds()) {

        // buscamos con Feign el schedule original y guardamos sus valores en un dto
        ScheduleDTO dto = scheduleAPI.getSchedule(scheduleId);

        // si existe y lo trae, agregamos su startTime a la lista "startTime"
        // y el dto a una lista de DTOs
        if (dto != null && dto.getStartTime() != null) {
            startTime.add(dto.getStartTime());
            scheduleDTO.add(dto);
        }
    }

        Theater theater = theaterMapper.toTheaterSet(theaterDTO, scheduleDTO);

        theaterRepo.save(theater);

    }

    public TheaterDTO fallbackCreateTheaterWithSchedule (Throwable throwable){

        return new TheaterDTO("Failed", 0, "Failed", null);
    }

    @Override
    public void deleteTheater(Long id){

        theaterRepo.deleteById(id);
    }

    @Override
    public TheaterDTO editTheater(Long id, TheaterDTO theaterDTO){

        Theater newTheater = theaterRepo.findById(id).orElse(null);

        List <LocalDateTime> startTime = new ArrayList<>();
        List<ScheduleDTO> scheduleDTO = new ArrayList<>();
        List<Long> scheduleIds = new ArrayList<>();

        for (Long scheduleId : theaterDTO.getScheduleIds()) {

            ScheduleDTO dto = scheduleAPI.getSchedule(scheduleId);

            if (dto != null && dto.getStartTime() != null) {
                startTime.add(dto.getStartTime());
                scheduleDTO.add(dto);
                scheduleIds.add(dto.getId());
            }
        }
        newTheater.setStartTime(startTime);
        newTheater.setName(theaterDTO.getName());
        newTheater.setCapacity(theaterDTO.getCapacity());
        newTheater.setScreenType(theaterDTO.getScreenType());
        theaterRepo.save(newTheater);

        TheaterDTO newTheaterDTO = theaterMapper.toTheaterDTO(newTheater, scheduleIds);
        return newTheaterDTO;

        // EDITAR Y REUTILIZAR CODIGO DEL MÉTODO CREATE
    }

}

