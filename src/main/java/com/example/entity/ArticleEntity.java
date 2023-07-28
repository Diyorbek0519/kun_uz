package com.example.entity;

import com.example.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;


    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "shared_count")
    private Integer sharedCount;

    @Column(name = "region_id", nullable = false)
    private Integer regionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "moderator_id", nullable = false)
    private Integer moderatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id",insertable = false,updatable = false)
    private ProfileEntity moderator;

    @Column(name = "publisher_id")
    private Integer publisherId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", updatable = false, insertable = false)
    private ProfileEntity publisher;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatus status = ArticleStatus.NOT_PUBLISHED;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "visible")
    private Boolean visible = true;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "image_id", nullable = false)
    private String imageId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id",insertable = false, updatable = false)
    private AttachEntity image;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    private Set<ArticleTypesEntity> articleTypesSet;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "article")
    private Set<ArticleTagsEntity> articleTagsSet;


}
