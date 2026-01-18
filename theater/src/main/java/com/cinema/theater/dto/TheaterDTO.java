
package com.cinema.theater.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TheaterDTO {

    private Long id;
    private String name;
    private int capacity;
    private String screenType;
    
    private List<Long> scheduleIds = new ArrayList<>();
}
