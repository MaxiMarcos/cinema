/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maximarcos.cinema.service.impl;

import com.maximarcos.cinema.dto.MovieDTO;
import com.maximarcos.cinema.entity.Movie;
import com.maximarcos.cinema.enums.Billboard;
import com.maximarcos.cinema.enums.Category;
import com.maximarcos.cinema.mapper.MovieMapper;
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
    @Autowired
    MovieMapper movieMapper;

    @Override
    public List<MovieDTO> getAllMovie() {

        List<Movie> movies = movieRepo.findAll();
        List<MovieDTO> moviesDTO = movieMapper.toListMovieDTO(movies);
        return moviesDTO;
    }

    @Override
    public MovieDTO findMovie(Long id) {

        Movie movie = movieRepo.findById(id).orElse(null);
        MovieDTO movieDTO = movieMapper.toMovieDTO(movie);

        return movieDTO;
    }

    @Override
    public Movie findMovieNoDTO(Long id) {

        return movieRepo.findById(id).orElse(null);
    }

    @Override
    public List<MovieDTO> findMovieByCategory(String category) {

        Category cat = Category.valueOf(category.toUpperCase());
        return movieRepo.findMoviesByCategory(cat);
    }

    @Override
    public List<MovieDTO> findMovieByBillboard(String billboard) {

        Billboard bil = Billboard.valueOf(billboard.toUpperCase());

        return movieRepo.findMoviesByBillboard(bil);
    }

    @Override
    public void deleteMovie(Long id) {

        movieRepo.deleteById(id);
    }

    @Override
    public MovieDTO createMovie(Movie movie) {

        movieRepo.save(movie);
        MovieDTO movieDTO = movieMapper.toMovieDTO(movie);
        return movieDTO;
    }

    @Override
    public MovieDTO editMovie(Long id, Movie movie) {

            Movie themovie = movieRepo.findById(id).orElse(null);
            themovie.setLanguage(movie.getLanguage());
            themovie.setName(movie.getName());
            themovie.setSubtitle(movie.getSubtitle());
            themovie.setCategory(movie.getCategory());
            themovie.setBillboard(movie.getBillboard());
            themovie.setDescription(movie.getDescription());
            movieRepo.save(themovie);

            MovieDTO movieDTO = movieMapper.toMovieDTO(themovie);
            return movieDTO;
    }


    @Override
    public boolean existsByName(String name) {
        return movieRepo.existsByName(name);
    }

}
