
package com.maximarcos.cinema.service;

import com.maximarcos.cinema.dto.ScheduleDTO;
import com.maximarcos.cinema.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
    
    public List<Schedule> getAllSchedule();   
    public Schedule findSchedule(Long id);
    public List<ScheduleDTO> findScheduleByMovie(Long movieId);
    public void deleteSchedule(Long id);
    public void createSchedule (ScheduleDTO scheduleDTO);
    public void editSchedule(Long id, ScheduleDTO scheduleDTO);
    
}
