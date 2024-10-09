
package com.maximarcos.cinema.repository;

import com.maximarcos.cinema.dto.ScheduleDTO;
import com.maximarcos.cinema.entity.Movie;
import com.maximarcos.cinema.entity.Schedule;
import com.maximarcos.cinema.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.movie.id = :movieId")
    List<Schedule> findSchedulesByMovie(@Param("movieId") Long movieId);
}
