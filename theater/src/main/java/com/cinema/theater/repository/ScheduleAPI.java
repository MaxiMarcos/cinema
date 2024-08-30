
package com.cinema.theater.repository;

import com.cinema.theater.dto.ScheduleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="scheduleAPI", url="http://localhost:9001/schedule")
public interface ScheduleAPI {
    
    @GetMapping("/find/{scheduleId}")
    public ScheduleDTO getSchedule(@PathVariable("scheduleId")Long scheduleId);
    
}
