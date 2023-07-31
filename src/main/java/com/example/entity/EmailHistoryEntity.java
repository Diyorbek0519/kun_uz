package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="email_history")
public class EmailHistoryEntity extends BaseEntity {
    @Column(name = "message",columnDefinition = "TEXT")
    private String message;
    @Column(name = "email",nullable = false)
    private String email;
}
