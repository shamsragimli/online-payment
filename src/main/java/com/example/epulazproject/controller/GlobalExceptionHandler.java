package com.example.epulazproject.controller;

import com.example.epulazproject.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
        public ExceptionDto handler(NotFoundException exception){
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handler(ValidationException exception){
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto handler(MethodArgumentNotValidException exception){
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDto handler(Exception exception){
        log.error("ActionLog.handler.error: message {} ", exception.getMessage());
        return new ExceptionDto("UNEXCEPTED_EXCEPTION");
    }

    @ExceptionHandler(NotEnoughBalance.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ExceptionDto handler(NotEnoughBalance exception){
        log.error("ActionLog.handler.error: message {} ", exception.getMessage());
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionDto handler(AlreadyExistsException exception){
        log.error("ActionLog.handler.error: message {} ", exception.getMessage());
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDto handler(EntityNotFoundException exception){
        log.error("ActionLog.handler.error: message {} ", exception.getMessage());
        return new ExceptionDto(exception.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionDto handleForbiddenException(ForbiddenException exception) {
        log.error("ActionLog.handler.error: message {} ", exception.getMessage());
        return new ExceptionDto(exception.getMessage());
    }

}
