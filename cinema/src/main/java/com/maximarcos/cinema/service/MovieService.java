/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maximarcos.cinema.service;

import com.maximarcos.cinema.dto.MovieDTO;
import com.maximarcos.cinema.entity.Movie;
import com.maximarcos.cinema.enums.Billboard;
import com.maximarcos.cinema.enums.Category;
import java.util.List;
import org.springframework.stereotype.Service;

public interface MovieService {
    
    public List<MovieDTO> getAllMovie();
    public MovieDTO findMovie(Long id);
    public List<MovieDTO> findMovieByCategory(String category);
    public List<MovieDTO> findMovieByBillboard(String billboard);
    public void deleteMovie(Long id);
    public MovieDTO createMovie (Movie movie);
    public MovieDTO editMovie(Long id, Movie movie);

    boolean existsByName(String name);
}
