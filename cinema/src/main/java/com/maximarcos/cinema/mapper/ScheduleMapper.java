package com.maximarcos.cinema.mapper;

import com.maximarcos.cinema.dto.MovieDTO;
import com.maximarcos.cinema.dto.ScheduleDTO;
import com.maximarcos.cinema.entity.Movie;
import com.maximarcos.cinema.entity.Schedule;
import com.maximarcos.cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleMapper {

    @Autowired
    MovieService movieService;

    public ScheduleDTO toScheduleDTO(Schedule schedule){

        Movie movie = movieService.findMovieNoDTO(schedule.getId());

        return ScheduleDTO.builder()
                .startTime(schedule.getStartTime())
                .movie_id(movie.getId())
                .build();

    }

    public List<ScheduleDTO> toListScheduleDTO(List<Schedule> schedules){

        if(schedules == null){
            System.out.println("LIST NULL");
        }

        List<ScheduleDTO> newList = new ArrayList<>();
        for(Schedule s : schedules){
            newList.add(toScheduleDTO(s));
        }
        return newList;

    }
}
