package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sms_history")
public class SmsHistoryEntity extends BaseEntity{
    @Column(name = "message",columnDefinition = "TEXT")
    private String message;
    @Column(name = "phone",nullable = false)
    private String phone;
}
