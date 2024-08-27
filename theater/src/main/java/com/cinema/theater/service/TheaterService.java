
package com.cinema.theater.service;

import com.cinema.theater.dto.TheaterDTO;
import com.cinema.theater.entity.Theater;
import java.time.LocalDateTime;
import java.util.List;

public interface TheaterService {

    void createTheater(String name, int capacity, List<Long> scheduleIds,
                       String screenType);

    void deleteTheater(Long id);

    void editTheater(Long id, Theater theater);

    List<Theater> getAllTheater();

    Theater getTheater(Long id);
    
}
