package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class TagEntity extends BaseEntity{

    @Column(name = "name_uz",nullable = false)
    private String nameUz;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_en")
     private String nameEn;

    @Column(name = "prt_id")
    private Integer prtId;

}
