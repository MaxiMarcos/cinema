
package com.maximarcos.cinema.service.impl;

import com.maximarcos.cinema.dto.ScheduleDTO;
import com.maximarcos.cinema.entity.Movie;
import com.maximarcos.cinema.entity.Schedule;
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
    MovieService movieService;
    
    @Override
    public List<Schedule> getAllSchedule() {
        return scheduleRepo.findAll();
    }

    @Override
    public Schedule findSchedule(Long id) {
        return scheduleRepo.findById(id).orElse(null);
    }

    @Override
    public List<ScheduleDTO> findScheduleByMovie(Long movieId) {

        List<Schedule> schedules = scheduleRepo.findSchedulesByMovie(movieId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();

        List<LocalDateTime> aaaa = new ArrayList<>();

        // Convertir cada Schedule en ScheduleDTO
        for (Schedule schedule : schedules) {
            ScheduleDTO dto = new ScheduleDTO();
            dto.setStartTime(schedule.getStartTime()); // Asignar startTime
            scheduleDTOs.add(dto); // Agregar el DTO a la lista
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

        Movie movie = movieService.findMovie2(scheduleDTO.getMovie_id());

        System.out.println("La movie capturada es:" + movie);

        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setMovie(movie);

        scheduleRepo.save(schedule);

    }

    @Override
    public void editSchedule(Long id, ScheduleDTO scheduleDTO) {

        Schedule sch = scheduleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));

        Movie movie = movieService.findMovie2(scheduleDTO.getMovie_id());

        sch.setMovie(movie);
        sch.setStartTime(scheduleDTO.getStartTime());
        
        scheduleRepo.save(sch);
    }
}
