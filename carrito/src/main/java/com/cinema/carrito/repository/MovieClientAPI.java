package com.cinema.carrito.repository;


import com.cinema.carrito.dto.MovieDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="cinema")
public interface MovieClientAPI {

    @GetMapping("/movie/find/{movieId}")
    public MovieDTO getMovie(@PathVariable("movieId")Long movieId);

}
