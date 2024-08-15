package com.example.epulazproject.model.response;

import com.example.epulazproject.enums.SupportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupportResponseDto {
    private Long id;
    private String userId;
    private String issue;
    private String feedback;
    private SupportStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
