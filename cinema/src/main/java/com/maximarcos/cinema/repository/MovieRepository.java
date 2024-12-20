
package com.maximarcos.cinema.repository;

import com.maximarcos.cinema.dto.MovieDTO;
import com.maximarcos.cinema.entity.Movie;
import com.maximarcos.cinema.enums.Billboard;
import com.maximarcos.cinema.enums.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    
    @Query("SELECT m FROM Movie m WHERE m.category = :category")
    List<MovieDTO> findMoviesByCategory(@Param("category") Category category);

    @Query("SELECT m FROM Movie m WHERE m.billboard = :billboard")
    List<MovieDTO> findMoviesByBillboard(@Param("billboard") Billboard billboard);

    boolean existsByName(String name);
}
