/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maximarcos.cinema.service.impl;

import com.maximarcos.cinema.dto.MovieDTO;
import com.maximarcos.cinema.entity.Movie;
import com.maximarcos.cinema.enums.Billboard;
import com.maximarcos.cinema.enums.Category;
import com.maximarcos.cinema.repository.MovieRepository;
import com.maximarcos.cinema.service.MovieService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.criteria.Path;
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
    public MovieDTO findMovie(Long id) {

        Movie movie = movieRepo.findById(id).orElse(null);

        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setBillboard(movie.getBillboard());
        movieDTO.setName(movie.getName());
        movieDTO.setPhoto(movie.getPhoto());
        movieDTO.setLanguage(movie.getLanguage());
        movieDTO.setCategory(movie.getCategory());
        movieDTO.setDescription(movie.getDescription());
        movieDTO.setSubtitle(movie.getSubtitle());

        return movieDTO;
    }

    @Override
    public Movie findMovie2(Long id) {
        return movieRepo.findById(id).orElse(null);
    }

    @Override
    public List<Movie> findMovieByCategory(String category) {

        Category cat = Category.valueOf(category.toUpperCase()); // Convierte a mayúsculas para comparación
        return movieRepo.findMoviesByCategory(cat);
    }

    @Override
    public List<Movie> findMovieByBillboard(String billboard) {

        Billboard bil = Billboard.valueOf(billboard.toUpperCase()); // Convierte a mayúsculas para comparación

        return movieRepo.findMoviesByBillboard(bil);
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

        Movie themovie = this.findMovie2(id);

        themovie.setLanguage(movie.getLanguage());
        themovie.setName(movie.getName());
        themovie.setSubtitle(movie.getSubtitle());
        themovie.setCategory(movie.getCategory());
        themovie.setBillboard(movie.getBillboard());
        themovie.setDescription(movie.getDescription());
        movieRepo.save(themovie);
    }

    @Override
    public boolean existsByName(String name) {
        return movieRepo.existsByName(name);
    }

}
