
package com.cinema.theater.entity;

import com.cinema.theater.dto.ScheduleDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Theater {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int capacity;
    private String screenType;
    
    @ElementCollection // se almacena en una tabla separada/intermedia
    private List<LocalDateTime> startTime = new ArrayList<>();


}
