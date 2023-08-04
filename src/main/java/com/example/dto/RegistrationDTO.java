package com.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    @NotNull(message = "Name is required")
    @Size(min = 3, message = "Name should be at least 3 characters")
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
}
