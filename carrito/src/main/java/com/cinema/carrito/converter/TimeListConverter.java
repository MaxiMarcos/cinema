package com.cinema.carrito.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class TimeListConverter implements AttributeConverter<List<LocalDateTime>, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public String convertToDatabaseColumn(List<LocalDateTime> attribute) {

        return attribute.stream()
                .map(date -> date.format(FORMATTER))
                .collect(Collectors.joining(",")); // Convertir la lista en un String separado por comas
    }

    @Override
    public List<LocalDateTime> convertToEntityAttribute(String dbData) {

        if (dbData == null || dbData.isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(dbData.split(","))
                .map(date -> LocalDateTime.parse(date, FORMATTER))
                .collect(Collectors.toList());
    }
}