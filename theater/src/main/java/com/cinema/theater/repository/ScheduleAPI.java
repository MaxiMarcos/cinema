
package com.cinema.theater.repository;

import com.cinema.theater.dto.ScheduleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="cinema")
public interface ScheduleAPI {
    
    @GetMapping("/schedule/find/{scheduleId}")
    ScheduleDTO getSchedule(@PathVariable("scheduleId")Long scheduleId);

}
