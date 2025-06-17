
package com.maximarcos.cinema.service.impl;

import com.maximarcos.cinema.dto.ScheduleDTO;
import com.maximarcos.cinema.entity.Movie;
import com.maximarcos.cinema.entity.Schedule;
import com.maximarcos.cinema.mapper.ScheduleMapper;
import com.maximarcos.cinema.repository.MovieRepository;
import com.maximarcos.cinema.repository.ScheduleRepository;
import com.maximarcos.cinema.service.MovieService;
import com.maximarcos.cinema.service.ScheduleService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepo;

    @Autowired
    MovieRepository movieRepo;

    @Autowired
    ScheduleMapper scheduleMapper;
    
    @Override
    public List<ScheduleDTO> getAllSchedule() {
        List<Schedule> schedules = scheduleRepo.findAll();

        return scheduleMapper.toListScheduleDTO(schedules);
    }

    @Override
    public ScheduleDTO findSchedule(Long id) {

        Schedule schedule = scheduleRepo.findById(id).orElse(null);
        return scheduleMapper.toScheduleDTO(schedule);
    }

    @Override
    public List<ScheduleDTO> findScheduleByMovie(Long movieId) {

        List<Schedule> schedules = scheduleRepo.findSchedulesByMovie(movieId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();

        // Convertir cada Schedule en ScheduleDTO para retornarlos
        for (Schedule schedule : schedules) {
            ScheduleDTO dto = new ScheduleDTO();
            dto.setStartTime(schedule.getStartTime());
            scheduleDTOs.add(dto);
        }

        return scheduleDTOs;
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepo.deleteById(id);
    }

    @Override
    public void createSchedule(ScheduleDTO scheduleDTO) {

        if (scheduleDTO.getMovie_id() == null) {
            throw new IllegalArgumentException("Movie ID must not be null");
        }

        Schedule schedule = new Schedule();

        Movie movie = movieRepo.findById(scheduleDTO.getMovie_id()).orElse(null);

        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setMovie(movie);

        scheduleRepo.save(schedule);
    }

    @Override
    public ScheduleDTO editSchedule(Long id, ScheduleDTO scheduleDTO) {

        Schedule sch = scheduleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));

        Movie movie = movieRepo.findById(scheduleDTO.getMovie_id()).orElse(null);

        sch.setMovie(movie);
        sch.setStartTime(scheduleDTO.getStartTime());
        
        scheduleRepo.save(sch);

        return scheduleMapper.toScheduleDTO(sch);
    }
}
