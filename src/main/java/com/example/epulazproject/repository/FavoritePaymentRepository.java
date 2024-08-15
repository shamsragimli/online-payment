package com.example.epulazproject.repository;

import com.example.epulazproject.dao.FavoritePaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritePaymentRepository extends JpaRepository<FavoritePaymentEntity,Integer> {
    List<FavoritePaymentEntity> findByUserId(Integer id);
}
