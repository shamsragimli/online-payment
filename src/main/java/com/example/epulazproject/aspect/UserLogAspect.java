package com.example.epulazproject.aspect;

import com.example.epulazproject.dao.UserEntity;
import com.example.epulazproject.dao.UserLog;
import com.example.epulazproject.model.request.LoginReq;
import com.example.epulazproject.repository.UserLogRepository;
import com.example.epulazproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Aspect
@Component
@Configurable
public class UserLogAspect {
        private final UserLogRepository userLogRepository;
        private final UserRepository userRepository;

        @SneakyThrows
        @AfterReturning("execution(* com.example.epulazproject.service.serviceImpl.AuthServiceImpl.authenticate(..))")
        public void logSignIn(JoinPoint joinPoint) {
            UserLog userLog = new UserLog();
            LoginReq loginReq = (LoginReq) joinPoint.getArgs()[0];
            String username = loginReq.getUsername();

            Optional<UserEntity> userOptional = userRepository.findByUserName(username);
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                userLog.setUser(user);
                userLogRepository.save(userLog);
                log.info("User login logged with aspect {} ", username);
            }
        }
}



