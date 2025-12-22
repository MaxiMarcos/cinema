
package com.maximarcos.cinema.controller;

import com.maximarcos.cinema.dto.MovieDTO;
import com.maximarcos.cinema.entity.Movie;
import com.maximarcos.cinema.enums.Billboard;
import com.maximarcos.cinema.enums.Category;
import com.maximarcos.cinema.service.impl.MovieServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.maximarcos.cinema.validation.ExistsByName;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public ResponseEntity<MovieDTO> createMovie(@RequestBody Movie movie) {

        try {
            MovieDTO movieDTO = movieServ.createMovie(movie);
            return new ResponseEntity<>( movieDTO, HttpStatus.CREATED );
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/find-all")
    public ResponseEntity<List<MovieDTO>> findAllMovie(){

        try {
           return new ResponseEntity<>(movieServ.getAllMovie(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findMovie(@PathVariable Long id){

        try{
            MovieDTO movieDTO = movieServ.findMovie(id);
            return new ResponseEntity<>(movieDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("The movie was not available", HttpStatus.BAD_REQUEST);
        }
    }

    // Buscar películas según categoría
    @GetMapping("/findbycategory/{category}")
    public ResponseEntity<List<MovieDTO>> findMovieByCategory(@PathVariable String category){

        try {
            List<MovieDTO> movieDTOS = movieServ.findMovieByCategory(category);
            return ResponseEntity.ok(movieDTOS);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Buscar películas según si está en cartelera o próximamente
    @GetMapping("/findbybillboard/{billboard}")
    public ResponseEntity<List<MovieDTO>> findMovieByBillboard(@PathVariable String billboard){

        try {
            List<MovieDTO> movieDTOS = movieServ.findMovieByBillboard(billboard);
            return ResponseEntity.ok(movieDTOS);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findbylanguage/{language}")
    public ResponseEntity<List<MovieDTO>> findMovieByLanguage(@PathVariable String language){
        try{
            List<MovieDTO>movieDTOS = movieServ.findMovieByLanguage(language);
            return ResponseEntity.ok(movieDTOS);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id){

        try {
            movieServ.deleteMovie(id);
            return new ResponseEntity<>("The movie was deleted correctly", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("There was an error deleted the movie: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/edit/{id_original}")
    public ResponseEntity<?> editMovie(@PathVariable Long id_original,
                                    @Valid @RequestBody Movie movie){

        try {
            MovieDTO movieDTO = movieServ.editMovie(id_original, movie);
            return new ResponseEntity<>("The movie was edited correctly" + movieDTO, HttpStatus.CREATED);
        } catch(Exception e){

            return new ResponseEntity<>("There was an error edited the movie: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
