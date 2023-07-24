package com.example.entity;

import com.example.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "description",nullable = false)
    private String description;


    @Column(name = "content",nullable = false,columnDefinition ="TEXT")
    private String content;

    @Column(name ="shared_count")
    private Integer sharedCount;


    //private String imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private RegionEntity regionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_  id")
    private  ProfileEntity moderatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private ProfileEntity publisherId;

    @Column(name = "status")
    private ArticleStatus status=ArticleStatus.NOT_PUBLISHED;

    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "visible")
    private Boolean visible=true;

    @Column(name = "view_count")
    private Integer viewCount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id",referencedColumnName = "id")
    private AttachEntity attachEntity;


    @ManyToMany
    @JoinTable(
            name = "article_types",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "article_type_id"))
    List<ArticleTypeEntity> articleTypes;

}
