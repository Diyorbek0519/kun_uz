package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SmsHistoryDTO {
    private Integer id;
    private String phone;
    private String message;
    private LocalDateTime createdDate;
}
