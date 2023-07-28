package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO {
    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String name;
    private LocalDateTime createdDate;

    public TagDTO() {
    }

    public TagDTO(String name) {
        this.name = name;
    }
}