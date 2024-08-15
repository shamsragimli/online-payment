package com.example.epulazproject.model.response;

import com.example.epulazproject.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardResponseDto {
    private Integer id;
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private CardType cardType;
    private Double amount;




}
