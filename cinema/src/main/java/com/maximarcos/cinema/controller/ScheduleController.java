
package com.maximarcos.cinema.controller;

import com.maximarcos.cinema.entity.Schedule;
import com.maximarcos.cinema.service.impl.ScheduleServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    
    @Autowired
    ScheduleServiceImpl scheduleService;
    
    @GetMapping("/find-all")
    public List<Schedule> findAllSchedule(){
        return scheduleService.getAllSchedule();
    }
    
    @GetMapping("/find/{id}")
    public Schedule findSchedule(@PathVariable Long id){
        return scheduleService.findSchedule(id);
    }
    
    @PostMapping("/create")
    public ResponseEntity<String> createSchedule(@RequestBody Schedule schedule) {
        
        try {
            scheduleService.createSchedule(schedule);
            return new ResponseEntity<>("The schedule was created correctly", HttpStatus.CREATED);
        
        } catch (Exception e) {
            
            return new ResponseEntity<>("There was an error creating the schedule: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable Long id){
        
        scheduleService.deleteSchedule(id);
        return "Schedule deleted";
    }
    
    @PutMapping("/edit/{id_original}")
    public Schedule editSchedule(@PathVariable Long id_original,
                             @RequestBody Schedule schedule){
        
       scheduleService.editSchedule(id_original, schedule);
       
       Schedule sch = this.findSchedule(id_original);
       
       return sch;
    }
}
