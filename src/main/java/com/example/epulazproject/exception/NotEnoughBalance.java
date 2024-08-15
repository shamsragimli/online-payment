package com.example.epulazproject.exception;

public class NotEnoughBalance extends RuntimeException{
    public NotEnoughBalance(String message) {
        super(message);
    }
}
