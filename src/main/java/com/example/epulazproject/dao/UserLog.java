package com.example.epulazproject.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name= "user_log")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "last_login_date")
    @NotNull(message = "date cannot be null")
    private LocalDateTime lastLoginDate;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @PrePersist
    protected void onCreate() {
        lastLoginDate = LocalDateTime.now();
    }
}
