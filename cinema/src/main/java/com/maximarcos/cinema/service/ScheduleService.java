
package com.maximarcos.cinema.service;

import com.maximarcos.cinema.dto.ScheduleDTO;
import com.maximarcos.cinema.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
    
    public List<ScheduleDTO> getAllSchedule();
    public ScheduleDTO findSchedule(Long id);
    public List<ScheduleDTO> findScheduleByMovie(Long movieId);
    public void deleteSchedule(Long id);
    public void createSchedule (ScheduleDTO scheduleDTO);
    public ScheduleDTO editSchedule(Long id, ScheduleDTO scheduleDTO);
    
}
