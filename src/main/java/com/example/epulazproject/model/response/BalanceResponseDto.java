package com.example.epulazproject.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BalanceResponseDto {
    private Integer id;
    private Double amount;
    private Double cashbackAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}
