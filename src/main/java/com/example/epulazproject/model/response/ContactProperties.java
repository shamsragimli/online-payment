package com.example.epulazproject.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "contact")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactProperties {
    private String name;
    private String email;
    private String phone;
    private String address;
}
