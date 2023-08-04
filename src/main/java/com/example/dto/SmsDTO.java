package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SmsDTO {
    //id,company_id,phone,message,created_date
    private Integer id;
    private Integer companyId;
    private String phone;
    private String message;
    private LocalDateTime createdDate;
}