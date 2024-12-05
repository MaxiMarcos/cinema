package com.maximarcos.cinema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maximarcos.cinema.enums.Billboard;
import com.maximarcos.cinema.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // ignora y no retorna los valores null (me sirve para la función findScheduleByMovie)
public class MovieDTO {

    private String name;
    private String language;
    private String subtitle;
    private String description;
    private String photo;

    private Category category;

    private Billboard billboard;
}
