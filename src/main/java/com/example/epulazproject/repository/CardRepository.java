package com.example.epulazproject.repository;

import com.example.epulazproject.dao.CardEntity;
import com.example.epulazproject.dao.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity,Integer> {

}
