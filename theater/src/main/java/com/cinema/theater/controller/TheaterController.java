
package com.cinema.theater.controller;

import com.cinema.theater.dto.ScheduleDTO;
import com.cinema.theater.dto.TheaterDTO;
import com.cinema.theater.entity.Theater;
import com.cinema.theater.service.TheaterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public Theater findTheater(@PathVariable Long id){
        
        return theaterServ.getTheater(id);
    }
    
    @PostMapping("/create")
    public void createTheater(@RequestBody TheaterDTO theaterDTO){
        theaterServ.createTheater(theaterDTO.getName(), theaterDTO.getCapacity(), theaterDTO.getScheduleIds(),
                                   theaterDTO.getScreenType());
    }
    
    @DeleteMapping("/delete/{id}")
    public void deleteTheater(@PathVariable Long id){
        
        theaterServ.deleteTheater(id);
    }
    
    @PutMapping("/edit/{id_original}")
    public Theater editTheater(@PathVariable Long id_original,
                            @RequestBody Theater theater){
        
        theaterServ.editTheater(id_original, theater);
        Theater th = this.findTheater(id_original);
        
        return th;
    }
    
    
            
}
