package com.cinema.carrito.repository;


import com.cinema.carrito.dto.ScheduleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="cinema")
public interface ScheduleClientAPI {

   //@GetMapping("/schedule/find/{scheduleId}")
   //public ScheduleDTO getSchedule(@PathVariable("scheduleId")Long scheduleId);

}