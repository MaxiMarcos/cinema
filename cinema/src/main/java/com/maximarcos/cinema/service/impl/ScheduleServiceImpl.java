
package com.maximarcos.cinema.service.impl;

import com.maximarcos.cinema.dto.ScheduleDTO;
import com.maximarcos.cinema.entity.Movie;
import com.maximarcos.cinema.entity.Schedule;
import com.maximarcos.cinema.repository.MovieRepository;
import com.maximarcos.cinema.repository.ScheduleRepository;
import com.maximarcos.cinema.service.MovieService;
import com.maximarcos.cinema.service.ScheduleService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Schedule> findScheduleByMovie(Long movieId) {

        return scheduleRepo.findSchedulesByMovie(movieId);
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepo.deleteById(id);
    }

    @Override
    public void createSchedule(ScheduleDTO scheduleDTO) {

        Schedule schedule = new Schedule();

        Movie movie = movieService.findMovie(scheduleDTO.getMovie_id());

        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setMovie(movie);

        scheduleRepo.save(schedule);

    }

    @Override
    public void editSchedule(Long id, Schedule schedule) {
        
        Schedule sch = this.findSchedule(id);
        sch.setMovie(schedule.getMovie());
        sch.setStartTime(schedule.getStartTime());
        
        scheduleRepo.save(sch);
    }
}
