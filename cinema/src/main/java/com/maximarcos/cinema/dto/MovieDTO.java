package com.maximarcos.cinema.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.maximarcos.cinema.enums.Billboard;
import com.maximarcos.cinema.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // ignora y no retorna los valores null (me sirve para la función findScheduleByMovie)
public class MovieDTO {

    private Long id;
    private String name;
    private String language;
    private String subtitle;
    private String description;
    private String photo;

    private Category category;

    private Billboard billboard;
}
