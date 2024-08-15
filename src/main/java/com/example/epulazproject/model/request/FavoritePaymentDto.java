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
public class FavoritePaymentDto {
    @NotBlank(message = "Nickname cannot be blank")
    @Size(max = 50, message = "Nickname cannot be longer than 50 characters")
    private String nickname;

    @NotNull(message = "User ID cannot be null")
    @Min(value = 1, message = "User ID must be a positive integer")
    private Integer userId;

    @NotNull(message = "Payment ID cannot be null")
    @Min(value = 1, message = "Payment ID must be a positive integer")
    private Integer paymentId;

    @NotNull(message = "Field ID cannot be null")
    @Min(value = 1, message = "Field ID must be a positive integer")
    private Integer fieldId;
}
