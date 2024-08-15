package com.example.epulazproject.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FutureExpirationDateValidator implements ConstraintValidator<FutureExpirationDate, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yy");

    @Override
    public void initialize(FutureExpirationDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false; // Null ve ya boş qebul edilmir
        }

        try {
            YearMonth expirationDate = YearMonth.parse(value, FORMATTER);
            YearMonth now = YearMonth.now();
            return expirationDate.isAfter(now);
        } catch (DateTimeParseException e) {
            return false; // Sehv formatda tarix olsa qebul edilmir
        }
    }
}
