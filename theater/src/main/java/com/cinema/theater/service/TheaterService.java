
package com.cinema.theater.service;

import com.cinema.theater.dto.TheaterDTO;
import com.cinema.theater.entity.Theater;
import java.util.List;

public interface TheaterService {

    void createTheater(TheaterDTO theaterDTO);

    void deleteTheater(Long id);

    TheaterDTO editTheater(Long id, TheaterDTO theaterDTO);

    List<TheaterDTO> getAllTheater();

    TheaterDTO getTheater(Long id);
    
}
