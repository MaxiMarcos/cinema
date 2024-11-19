
package com.cinema.theater.entity;

import com.cinema.theater.dto.ScheduleDTO;
import jakarta.persistence.*;

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
@Entity
public class Theater {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int capacity;
    private String screenType;
    
    @ElementCollection // Indica que los elementos de la colección no son entidades por sí mismos,
    // sino valores que deben almacenarse en una tabla separada/intermedia
    private List<LocalDateTime> startTime = new ArrayList<>();
    // CAMBIAR ESTO HACIA UNA RELACIÓN DE ENTIDADES PARA QUE STARTIME FORME PARTE DE LA ENTIDAD THEATER


}
