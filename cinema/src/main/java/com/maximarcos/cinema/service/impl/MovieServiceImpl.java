/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maximarcos.cinema.service.impl;

import com.maximarcos.cinema.entity.Movie;
import com.maximarcos.cinema.enums.Category;
import com.maximarcos.cinema.repository.MovieRepository;
import com.maximarcos.cinema.service.MovieService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepo;

    @Override
    public List<Movie> getAllMovie() {
        return movieRepo.findAll();
    }

    @Override
    public Movie findMovie(Long id) {
        return movieRepo.findById(id).orElse(null);
    }
    
    @Override
    public List<Movie> findMoviesByCategory(Category category) {
        
        return movieRepo.findMoviesByCategory(category);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepo.deleteById(id);
    }

    @Override
    public void createMovie(Movie movie) {
        movieRepo.save(movie);
    }

    @Override
    public void editMovie(Long id, Movie movie) {
        Movie themovie = this.findMovie(id);
        themovie.setLanguage(movie.getLanguage());
        themovie.setName(movie.getName());
        themovie.setSubtitle(movie.getSubtitle());
    }
}
