package com.example.epulazproject.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "authorities")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Authority {
    @Id
    private String name;
}
