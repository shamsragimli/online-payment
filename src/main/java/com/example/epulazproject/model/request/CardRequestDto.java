package com.example.epulazproject.model.request;

import com.example.epulazproject.enums.CardType;
import com.example.epulazproject.validation.FutureExpirationDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardRequestDto {
    @NotBlank(message = "Card number cannot be blank")
    @Pattern(regexp = "\\d{16}", message = "Card number must be exactly 16 digits")
    private String cardNumber;

    @NotBlank(message = "Card holder name cannot be blank")
    @Size(max = 100, message = "Card holder name cannot be longer than 100 characters")
    private String cardHolderName;

    @NotNull(message = "Expiration date cannot be null")
    @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "Expiration date must be in MM/YY format")
    @FutureExpirationDate(message = "Expiration date must be in the future")
    private String expirationDate;

    @NotBlank(message = "CVV cannot be blank")
    @Pattern(regexp = "\\d{3,4}", message = "CVV must be 3 or 4 digits")
    private String cvv;

    @NotNull(message = "Card type cannot be null")
    private CardType cardType;

    @NotNull(message = "User ID cannot be null")
    @Min(value = 1, message = "User ID must be a positive integer")
    private Integer userId;
}
