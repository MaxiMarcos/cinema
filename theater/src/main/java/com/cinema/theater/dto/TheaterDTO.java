
package com.cinema.theater.dto;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TheaterDTO {

    private String name;
    private int capacity;
    private String screenType;
    
    private List<Long> scheduleIds = new ArrayList<>();
}
