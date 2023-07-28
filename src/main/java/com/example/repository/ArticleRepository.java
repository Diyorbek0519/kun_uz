package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.entity.ArticleTypeEntity;
import com.example.enums.ArticleStatus;
import com.example.mapper.ArticleFullInfoIMapper;
import com.example.mapper.ShortInfoMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {
    @Query("from ArticleEntity as a " +
            "inner join a.articleTypesSet as at " +
            "where at.articleTypeId=:articleTypeId and a.status=PUBLISHED and a.visible=true " +
            "order by a.publishedDate desc limit :limit")
    List<ArticleEntity> getLastArticlesByArticleTypeId(@Param("articleTypeId") Integer articleTypeId,
                                                       @Param("limit") int limit);

    @Query(value = "select a.id, a.title , a.description , a.image_id as imageId ," +
            " a.published_date as publishedDate from article as a" +
            " where a.id not in (:list) " +
            " order by a.published_date desc limit 8", nativeQuery = true)
    List<ShortInfoMapper> getLastWhereNotInList(@Param("list") List<String> articleId);

    @Query(value = "select  a.id, a.title, a.description, a.content," +
            " a.shared_count as sharedCount, a.region_id as regionId, " +
            "a.category_id as categoryId, a.published_date as publishedDate, a.view_count as wievCount" +
            "  from article as a " +
            "where id=:articleId and status='PUBLISHED'", nativeQuery = true)
    ArticleFullInfoIMapper getArticleByIdAndLang(@Param("articleId") String articleId);

    @Query(value = "select distinct (a.id), a.title , a.description , a.image_id as imageId , " +
            "a.published_date as publishedDate from article as a " +
            "inner join article_types as b " +
            " on a.id=b.article_id " +
            "where a.id not in (:articleId) and b.article_type_id in (:articleTypes) and a.visible=true and a.status='PUBLISHED'" +
            "order by a.published_date desc limit 4", nativeQuery = true)
    List<ShortInfoMapper> getLast4ArticlesByTypeExceptGivenArticle(@Param("articleId") String articleId,
                                                                   @Param("articleTypes") List<Integer> articleTypes);

    @Query(value = "select a.id, a.title, a.description, " +
            "a.image_id as imageId, a.published_date as publishedDate" +
            " from article as a where a.visible=true " +
            " order by a.view_count desc, a.published_date desc limit 1", nativeQuery = true)
    ShortInfoMapper getMostRead();

    @Query(value = "select distinct(a.id), a.title, a.description," +
            " a.image_id as imageId, a.published_date as publishedDate " +
            " from article as a" +
            " inner join article_tags as b " +
            " on a.id=b.article_id " +
            " where b.tag_id=:tagId and a.visible =true and a.status='PUBLISHED'" +
            " order by a.published_date desc" +
            " limit 4",nativeQuery = true)
    List<ShortInfoMapper> getLast4ArticleByTagId(@Param("tagId") Integer tagId);

    @Query(value = "select a.id, a.title, a.description," +
            " a.image_id as imageId, a.published_date as publishedDate " +
            " from article as a" +
            " inner join article_types as b " +
            " on a.id=b.article_id " +
            " where b.article_type_id in (:articleTypeId) and a.region_id=:regionId and a.visible =true and a.status='PUBLISHED'" +
            " order by a.published_date desc" +
            " limit 5 ",nativeQuery = true)
    List<ShortInfoMapper> getLast5ArticleByTypeAndRegion(@Param("articleTypeId") List<Integer> articleTypeId,
                                                         @Param("regionId") Integer regionId);

     @Query("from ArticleEntity as a where" +
             " a.regionId=:regionId and a.visible=true and a.status=PUBLISHED")
     PageImpl<ArticleEntity> getByRegionId(@Param("regionId") Integer regionId,
                                           Pageable pageable);

    @Query("from ArticleEntity as a where" +
            " a.categoryId=:categoryId and a.visible=true and a.status=PUBLISHED")
    PageImpl<ArticleEntity> getByCategoryId(@Param("categoryId") Integer categoryId,
                                          Pageable pageable);
    List<ArticleEntity> getTop5ByCategoryIdOrderByPublishedDate(Integer categoryId);



}
