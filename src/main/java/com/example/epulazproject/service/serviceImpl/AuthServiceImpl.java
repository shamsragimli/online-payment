package com.example.epulazproject.service.serviceImpl;


import com.example.epulazproject.dao.Authority;
import com.example.epulazproject.dao.UserEntity;
import com.example.epulazproject.exception.AlreadyExistsException;
import com.example.epulazproject.exception.ExceptionDto;

import com.example.epulazproject.mapper.UserMapper;
import com.example.epulazproject.model.request.LoginReq;
import com.example.epulazproject.model.response.LoginRes;
import com.example.epulazproject.model.request.UserRequestDto;
import com.example.epulazproject.repository.BalanceRepository;
import com.example.epulazproject.repository.UserRepository;
import com.example.epulazproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    private final BalanceRepository balanceRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public ResponseEntity<?> authenticate(LoginReq loginReq) {
        log.info("authenticate method started by: {}", loginReq.getUsername());
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(),
                            loginReq.getPassword()));
            log.info("authentication details: {}", authentication);
            String username = authentication.getName();
            UserEntity user = new UserEntity(username, "");
            String token = jwtUtil.createToken(user);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            LoginRes loginRes = new LoginRes(username, token);
            log.info("user: {} logged in", user.getUserName());
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(loginRes);

        } catch (BadCredentialsException e) {
            ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.BAD_REQUEST.name());
            log.error("Error due to {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDto);
        } catch (Exception e) {
            ExceptionDto exceptionDto = new ExceptionDto(HttpStatus.BAD_REQUEST.name());
            log.error("Error due to {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionDto);
        }
    }

    public void register(UserRequestDto userRequestDto) {
        log.info("ActionLog.register.start: userRequestDto {}", userRequestDto);
        if (userRepository.findByUserName(userRequestDto.getUserName()).isPresent()) {
            throw new AlreadyExistsException("USER_ALREADY_EXIST");
        }
        var userEntity = userMapper.mapToEntity(userRequestDto);
        userEntity.setUserName(userRequestDto.getUserName());
        userEntity.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        Authority authority = new Authority("USER");
        Set<Authority> authoritySet = Set.of(authority);
        userEntity.setAuthorities(authoritySet);
        userRepository.save(userEntity);
        Integer id = userEntity.getId();
        log.info("saved new user");
        userService.createBalance(id);
    }





//    @Transactional
//    public void createBalanceForNewUsers() {
//        List<UserEntity> newUserList = userRepository.findByCreatedAtAfter(LocalDateTime.now().minusSeconds(60));
//
//        for (UserEntity newUser : newUserList) {
//            if (newUser.getBalance() != null) {
//                continue;
//            }
//
//            BalanceEntity balanceEntity = new BalanceEntity();
//            balanceEntity.setUser(newUser);
//            balanceEntity.setAmount(0.0);
//
//            balanceRepository.save(balanceEntity);
//
//            newUser.setBalance(balanceEntity);
//            userRepository.save(newUser);
//        }
//    }
}


