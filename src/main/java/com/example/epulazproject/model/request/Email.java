package com.example.epulazproject.model.request;

import lombok.Data;

@Data
public class Email {
    private String receiver;
    private String subject;
    private String text;
}