package com.example.epulazproject;

import com.example.epulazproject.model.response.ContactProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
@EnableConfigurationProperties(ContactProperties.class)
public class EPulAzProjectApplication implements CommandLineRunner {
    private final BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(EPulAzProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        System.out.println(passwordEncoder.encode("string"));
//        System.out.println(passwordEncoder.encode("admin"));
    }
}
