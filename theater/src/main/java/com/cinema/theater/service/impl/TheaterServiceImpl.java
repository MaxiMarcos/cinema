
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public void createTheater(String name, int capacity, List<Long> scheduleIds,
                              String screenType ){
        
        // 1. Buscar schedules
        // 2. confirmar disponibilidad

        List <LocalDateTime> startTime = new ArrayList<>();
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
