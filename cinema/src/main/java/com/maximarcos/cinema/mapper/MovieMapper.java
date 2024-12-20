package com.maximarcos.cinema.mapper;

import com.maximarcos.cinema.dto.MovieDTO;
import com.maximarcos.cinema.entity.Movie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieMapper {

    public MovieDTO toMovieDTO(Movie movie){

        return MovieDTO.builder()
                .name(movie.getName())
                .category(movie.getCategory())
                .photo(movie.getPhoto())
                .description(movie.getDescription())
                .language(movie.getLanguage())
                .subtitle(movie.getSubtitle())
                .billboard(movie.getBillboard())
                .build();

    }

    public Movie toMovie(MovieDTO movieDTO){

        return Movie.builder()
                .name(movieDTO.getName())
                .category(movieDTO.getCategory())
                .photo(movieDTO.getPhoto())
                .description(movieDTO.getDescription())
                .language(movieDTO.getLanguage())
                .subtitle(movieDTO.getSubtitle())
                .billboard(movieDTO.getBillboard())
                .build();

    }

    public Movie movieToMovie(Movie movie){

        return Movie.builder()
                .name(movie.getName())
                .category(movie.getCategory())
                .photo(movie.getPhoto())
                .description(movie.getDescription())
                .language(movie.getLanguage())
                .subtitle(movie.getSubtitle())
                .billboard(movie.getBillboard())
                .build();

    }


    public List<MovieDTO> toListMovieDTO(List<Movie> movieList){

         if(movieList == null){
             System.out.println("LIST NULL");
         }

         List<MovieDTO> newList = new ArrayList<>();
         for(Movie m : movieList){
             newList.add(toMovieDTO(m));
         }
         return newList;
    }

}
