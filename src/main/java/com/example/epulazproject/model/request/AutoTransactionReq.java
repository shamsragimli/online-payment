package com.example.epulazproject.model.request;

import com.example.epulazproject.enums.AutoTransactionType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AutoTransactionReq {


    @NotNull(message = "User ID cannot be null")
    @Min(value = 1, message = "User ID must be a positive integer")
    private Integer userId;

    @NotNull(message = "Payment ID cannot be null")
    @Min(value = 1, message = "Payment ID must be a positive integer")
    private Integer paymentId;

    @NotNull(message = "Field ID cannot be null")
    @Min(value = 1, message = "Field ID must be a positive integer")
    private Integer fieldId;

    @NotBlank(message = "Nickname cannot be blank")
    @Size(max = 50, message = "Nickname cannot be longer than 50 characters")
    private String nickname;

    @NotNull(message = "Auto Transaction Type cannot be null")
    private AutoTransactionType autoTransactionType;

    @Future(message = "Transaction time must be in the future")
    private LocalDateTime transactionTime;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be a positive number")
    private Double amount;



}
