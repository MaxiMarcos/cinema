
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
import java.util.stream.Collectors;

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
    public List<TheaterDTO> getAllTheater(){

        List<TheaterDTO> theaterDTOS = theaterMapper.convertToListDto(theaterRepo.findAll());
        return  theaterDTOS;
    }

    @Override
    public TheaterDTO getTheater(Long id){

        Theater theater = theaterRepo.findById(id).orElse(null);
        TheaterDTO theaterDTO = theaterMapper.toTheaterDTOSinSchedule(theater);

        return theaterDTO;

    }

    @Override
    @CircuitBreaker(name="cinema", fallbackMethod = "fallbackCreateTheaterWithSchedule" )
    @Retry(name="cinema")
    public TheaterDTO createTheater(TheaterDTO theaterDTO) {
        // Procesa los ScheduleDTOs
        List<ScheduleDTO> scheduleDTOList = processScheduleIds(theaterDTO.getScheduleIds());

        Theater theater = theaterMapper.toTheaterSet(theaterDTO, scheduleDTOList);
        theaterRepo.save(theater);
        return theaterDTO;
    }

    public TheaterDTO fallbackCreateTheaterWithSchedule (Throwable throwable){

        return new TheaterDTO("Failed", 0, "Failed", null);
    }

    @Override
    public void deleteTheater(Long id){

        theaterRepo.deleteById(id);
    }

    @Override
    public TheaterDTO editTheater(Long id, TheaterDTO theaterDTO) {
        Theater theater = theaterRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Theater not found"));

        List<ScheduleDTO> scheduleDTOList = processScheduleIds(theaterDTO.getScheduleIds());
        List<LocalDateTime> startTimes = extractStartTimes(scheduleDTOList);;

        theater.setStartTime(startTimes);
        theater.setName(theaterDTO.getName());
        theater.setCapacity(theaterDTO.getCapacity());
        theater.setScreenType(theaterDTO.getScreenType());

        theaterRepo.save(theater);
        return theaterDTO;
    }


    private List<ScheduleDTO> processScheduleIds(List<Long> scheduleIds) {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Long scheduleId : scheduleIds) {
            ScheduleDTO dto = scheduleAPI.getSchedule(scheduleId);
            if (dto != null && dto.getStartTime() != null) {
                scheduleDTOList.add(dto);
            }
        }
        return scheduleDTOList;
    }

    private List<LocalDateTime> extractStartTimes(List<ScheduleDTO> scheduleDTOList) {
        return scheduleDTOList.stream()
                .map(ScheduleDTO::getStartTime)
                .collect(Collectors.toList());
    }

}

