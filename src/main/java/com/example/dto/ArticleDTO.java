package com.example.dto;

import com.example.enums.ArticleStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private Integer regionId;
    private Integer categoryId;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private Boolean visible;
    private Integer sharedCount;
    private Integer viewCount;
    private List<Integer> articleType;
    private List<Integer> tags;
    private String imageId;
    private AttachDTO image;
    private RegionDTO region;
    private CategoryDTO category;
    private List<TagDTO> tagDTO;
}
