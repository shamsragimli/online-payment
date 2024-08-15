package com.example.epulazproject.repository;

import com.example.epulazproject.dao.CardEntity;
import com.example.epulazproject.dao.FavoritePaymentEntity;
import com.example.epulazproject.dao.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity,Integer> {
      Optional<UserEntity> findByUserName(String username);
     List<UserEntity> findByCreatedAtAfter(LocalDateTime localDateTime);


}
