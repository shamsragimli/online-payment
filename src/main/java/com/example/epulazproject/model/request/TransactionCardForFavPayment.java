package com.example.epulazproject.model.request;

import com.example.epulazproject.enums.TransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionCardForFavPayment {

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be a positive number")
    private Double amount;

    @NotNull(message = "Transaction type cannot be null")
    private TransactionType transactionType;

    @NotNull(message = "User ID cannot be null")
    @Min(value = 1, message = "User ID must be a positive integer")
    private Integer userId;

    @NotNull(message = "Favorite Payment ID cannot be null")
    @Min(value = 1, message = "Favorite Payment ID must be a positive integer")
    private Integer favoritePaymentId;

    @NotNull(message = "Card ID cannot be null")
    @Min(value = 1, message = "Card ID must be a positive integer")
    private Integer cardId;
}

