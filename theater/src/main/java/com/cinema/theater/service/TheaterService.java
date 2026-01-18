
package com.cinema.theater.service;

import com.cinema.theater.dto.TheaterDTO;
import java.util.List;

public interface TheaterService {

    TheaterDTO createTheater(TheaterDTO theaterDTO);

    void deleteTheater(Long id);

    TheaterDTO editTheater(Long id, TheaterDTO theaterDTO);

    List<TheaterDTO> getAllTheater();

    TheaterDTO getTheater(Long id);
    
}
