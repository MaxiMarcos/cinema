
package com.cinema.theater.controller;

import com.cinema.theater.dto.ScheduleDTO;
import com.cinema.theater.dto.TheaterDTO;
import com.cinema.theater.entity.Theater;
import com.cinema.theater.service.TheaterService;
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
@RequestMapping("/theater")
public class TheaterController {
    
    @Autowired
    TheaterService theaterServ;
    
    @GetMapping("/find-all")
    public List<Theater> findAllTheater(){
    
        return theaterServ.getAllTheater();
    
}
    
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findTheater(@PathVariable Long id){
        
        try{
            Theater theater = theaterServ.getTheater(id);
            return new ResponseEntity<>(theater, HttpStatus.OK);
        }catch (Exception e){

            return new ResponseEntity<>("Theater was not available", HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/create")
    public ResponseEntity<TheaterDTO> createTheater(@RequestBody TheaterDTO theaterDTO){

        try{
            theaterServ.createTheater(theaterDTO);
            return new ResponseEntity<>(theaterDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTheater(@PathVariable Long id){
        
        try{
            theaterServ.deleteTheater(id);
            return new ResponseEntity<>("The theater was deleted", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("The theater was not deleted", HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/edit/{id_original}")
    public ResponseEntity editTheater(@PathVariable Long id_original,
                            @RequestBody TheaterDTO theaterDTO) {

        if (theaterDTO != null) {
            theaterServ.editTheater(id_original, theaterDTO);
            return new ResponseEntity<>(theaterDTO, HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Hay un error", HttpStatus.NO_CONTENT);
        }

    }
    
    
            
}
