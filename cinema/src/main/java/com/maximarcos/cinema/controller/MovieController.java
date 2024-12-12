
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
    public ResponseEntity<Object> createMovie(@Valid @RequestBody Movie movie, BindingResult bindingResult) {
        // Verificar si hay errores de validación
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        // Lógica del negocio
        try {
            movieServ.createMovie(movie);
            return new ResponseEntity<>(
                    "The movie was created correctly, your ID ORDER is: " + movie.getId(),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "There was an error creating the movie: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
    
    @GetMapping("/find-all")
    public List<MovieDTO> findAllMovie(){

        return movieServ.getAllMovie();
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
    public ResponseEntity findMovieByCategory(@PathVariable String category){

        try {

            return ResponseEntity.ok(movieServ.findMovieByCategory(category));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid category: " + category);
        }
    }

    // Buscar películas según si está en cartelera o próximamente
    @GetMapping("/findbybillboard/{billboard}")
    public ResponseEntity findMovieByBillboard(@PathVariable String billboard){

        try {

            return ResponseEntity.ok(movieServ.findMovieByBillboard(billboard));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid category: " + billboard);
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
