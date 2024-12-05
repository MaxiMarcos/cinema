package com.maximarcos.cinema.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsByNameValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsByName {
    String message() default "The name already exists"; // Mensaje de error
    Class<?>[] groups() default {}; // Grupos de validación
    Class<? extends Payload>[] payload() default {}; // Información adicional
}