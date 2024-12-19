
package com.maximarcos.cinema.controller;

import com.maximarcos.cinema.dto.ScheduleDTO;
import com.maximarcos.cinema.entity.Schedule;
import com.maximarcos.cinema.service.impl.ScheduleServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/find-all")
    public ResponseEntity<List<ScheduleDTO>> findAllSchedule(){

        try {
            return new ResponseEntity<>(scheduleService.getAllSchedule(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<ScheduleDTO> findSchedule(@PathVariable Long id){

        try {
            return new ResponseEntity<>(scheduleService.findSchedule(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findScheduleByMovie/{movieId}") // modificar a minuscula
    public ResponseEntity<?> findScheduleByMovie(@PathVariable Long movieId){

        try {
            List<ScheduleDTO> schedule = scheduleService.findScheduleByMovie(movieId);
            return ResponseEntity.ok(schedule); //
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }


        // modificar que devuelva solo el schedule y no el objeto completo

    }
    
    @PostMapping("/create")
    public ResponseEntity<String> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {


        try {
            scheduleService.createSchedule(scheduleDTO);
            return new ResponseEntity<>("The schedule was created correctly", HttpStatus.CREATED);
        
        } catch (Exception e) {
            
            return new ResponseEntity<>("There was an error creating the schedule: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id){

        try {
            scheduleService.deleteSchedule(id);
            return new ResponseEntity<>("Schedule deleted", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("There was an error deleted the schedule: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }
    
    @PutMapping("/edit/{id_original}")
    public ResponseEntity<ScheduleDTO> editSchedule(@PathVariable Long id_original,
                             @RequestBody ScheduleDTO scheduleDTO){

        try {

            return new ResponseEntity<>(scheduleService.editSchedule(id_original, scheduleDTO), HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
