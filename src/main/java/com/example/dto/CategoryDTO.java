package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    private Integer id;
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String name;
    private LocalDateTime createdDate;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer orderNumber, String name) {
        this.orderNumber = orderNumber;
        this.name = name;
    }

}
