package com.example.epulazproject.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldResponseDto {
    private Integer id;
    private String type;
    private String value;
}
