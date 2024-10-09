
package com.cinema.theater.service.impl;

import com.cinema.theater.dto.ScheduleDTO;
import com.cinema.theater.dto.TheaterDTO;
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
    public void createTheater(String name, int capacity, List<Long> scheduleIds,
                              String screenType ){
        
        // 1. Buscar schedules
        // 2. confirmar disponibilidad

        List <LocalDateTime> startTime = new ArrayList<>();
        List <String> movies = new ArrayList<>();
         for (Long scheduleId : scheduleIds) {

        ScheduleDTO dto = scheduleAPI.getSchedule(scheduleId);
        if (dto != null && dto.getStartTime() != null) {
            startTime.add(dto.getStartTime());
        }
    }
        
        Theater theater = new Theater();
        theater.setName(name);
        theater.setCapacity(capacity);
        theater.setScreenType(screenType);
        theater.setStartTime(startTime);
        
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
    public void editTheater(Long id, Theater theater){
        
        Theater theat = this.getTheater(id);
        theat.setCapacity(theater.getCapacity());
        theat.setName(theater.getName());
        theat.setScreenType(theater.getScreenType());
        
        theaterRepo.save(theat);
    }

}

