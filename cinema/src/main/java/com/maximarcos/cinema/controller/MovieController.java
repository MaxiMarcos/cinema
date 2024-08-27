
package com.maximarcos.cinema.controller;

import com.maximarcos.cinema.entity.Movie;
import com.maximarcos.cinema.service.impl.MovieServiceImpl;
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
@RequestMapping("/movie")
public class MovieController {
    
    @Autowired
    MovieServiceImpl movieServ;
    
    @PostMapping("/create")
    public ResponseEntity<String> createMovie(@RequestBody Movie movie){
        
        try {
            movieServ.createMovie(movie);
            return new ResponseEntity<>("The movie was created correctly", HttpStatus.CREATED);
        } catch(Exception e){
            
            return new ResponseEntity<>("There was an error creating the schedule: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    @GetMapping("/find-all")
    public List<Movie> findAllMovie(){
        
        return movieServ.getAllMovie();
    }
    
    @GetMapping("/find/{id}")
    public Movie findMovie(@PathVariable Long id){
        
        return movieServ.findMovie(id);
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id){
        
        movieServ.deleteMovie(id);
        return "The movie was deleted correctly";
    }
    
    @PutMapping("/edit/{id_original}")
    public Movie editMovie(@PathVariable Long id_original,
                           @RequestBody Movie movie){
        
        movieServ.editMovie(id_original, movie);
               
        Movie movieEdit = this.findMovie(id_original);
        return movieEdit;
    }
}
