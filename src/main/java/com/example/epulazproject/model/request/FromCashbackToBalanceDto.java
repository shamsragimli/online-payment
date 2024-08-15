package com.example.epulazproject.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FromCashbackToBalanceDto {
    @NotNull(message = "Balance ID cannot be null")
    @Min(value = 1, message = "Balance ID must be a positive integer")
    private Integer balanceId;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be a positive number")
    private Double transferAmount;
}
