package com.example.epulazproject.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldRequestDto {
    @NotNull(message = "Payment ID cannot be null")
    @Min(value = 1, message = "Payment ID must be a positive integer")
    private Integer paymentId;

    @NotBlank(message = "Type cannot be blank")
    @Size(max = 50, message = "Type cannot be longer than 50 characters")
    private String type;

    @NotBlank(message = "Value cannot be blank")
    @Size(max = 255, message = "Value cannot be longer than 255 characters")
    private String value;
}
