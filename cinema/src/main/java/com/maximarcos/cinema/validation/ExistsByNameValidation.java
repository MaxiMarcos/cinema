package com.maximarcos.cinema.validation;

import com.maximarcos.cinema.repository.MovieRepository;
import com.maximarcos.cinema.service.MovieService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistsByNameValidation implements ConstraintValidator<ExistsByName, String> {

    @Autowired
    private MovieRepository movieRepo;


    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return !movieRepo.existsByName(name);
    }
}
