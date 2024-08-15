package com.example.epulazproject.model.response;

import com.example.epulazproject.enums.GenderStatus;
import com.example.epulazproject.model.response.CardResponseDto;
import com.example.epulazproject.model.response.TransactionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {
    private Integer id;
    private String name;
    private String surname;
    private String userName;
    private LocalDate birthDate;
    private GenderStatus genderStatus;
    private List<CardResponseDto> cards;
    private List<TransactionResponseDto> transactions;
}
