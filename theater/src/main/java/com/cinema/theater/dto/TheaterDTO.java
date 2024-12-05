
package com.cinema.theater.dto;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TheaterDTO {

    private String name;
    private int capacity;
    private String screenType;
    
    private List<Long> scheduleIds = new ArrayList<>();
}
