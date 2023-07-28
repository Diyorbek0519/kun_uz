package com.example.repository;

import com.example.entity.ArticleTypesEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleTypesRepository extends CrudRepository<ArticleTypesEntity,Integer> {

    @Query("select a.articleTypeId from ArticleTypesEntity as a where a.articleId=:articleId")
    List<Integer> getAllByArticleId(@Param("articleId") String articleId);

    @Transactional
    @Modifying
    @Query("delete from ArticleTypesEntity as a where a.articleId=:articleId and a.articleTypeId=:typeId")
    int deleteByArticleIdAndTypeId(@Param("articleId") String articleId,
                                   @Param("typeId") Integer typeId);

    @Transactional
    @Modifying
    @Query("delete from ArticleTypesEntity as a where a.articleId=:articleId")
    int deleteByArticleId(@Param("articleId") String articleId);
}
