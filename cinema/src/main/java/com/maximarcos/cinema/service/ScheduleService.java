
package com.maximarcos.cinema.service;

import com.maximarcos.cinema.entity.Schedule;
import java.util.List;

public interface ScheduleService {
    
    public List<Schedule> getAllSchedule();   
    public Schedule findSchedule(Long id);
    public List<Schedule> findScheduleByMovie(Long movieId);
    public void deleteSchedule(Long id);
    public void createSchedule (Schedule schedule);
    public void editSchedule(Long id, Schedule schedule);
    
}
