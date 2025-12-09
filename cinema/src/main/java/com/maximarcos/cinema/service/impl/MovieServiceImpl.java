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


import java.util.List;

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
        return movieMapper.toListMovieDTO(movies);
    }

    @Override
    public MovieDTO findMovie(Long id) {

        Movie movie = movieRepo.findById(id).orElse(null);
        return movieMapper.toMovieDTO(movie);
    }

   // @Override
   // public Movie findMovieNoDTO(Long id) {

     //   return movieRepo.findById(id).orElse(null);
   // }

    @Override
    public List<MovieDTO> findMovieByCategory(String category) {

        Category cat = Category.valueOf(category.toUpperCase());
        return movieRepo.findMoviesByCategory(cat);
    }

    @Override
    public List<MovieDTO> findMovieByBillboard(String billboard) {

        Billboard bil = Billboard.valueOf(billboard.toUpperCase());

        List<Movie> movies = movieRepo.findMoviesByBillboard(bil);

        List<MovieDTO> moviesdto = movieMapper.toListMovieDTO(movies);
        return  moviesdto;
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
