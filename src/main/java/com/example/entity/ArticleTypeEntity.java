package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "article_type")
public class ArticleTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number",unique = true)
    private Integer orderNumber;

    @Column(name = "name_uz",nullable = false)
    private String nameUz;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "visible")
    private Boolean visible=true;

    @Column(name ="created_date")
    private LocalDateTime createdDate=LocalDateTime.now();
}
