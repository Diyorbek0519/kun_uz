package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class AuthDTO {
    private String phone;
    private String password;
}
