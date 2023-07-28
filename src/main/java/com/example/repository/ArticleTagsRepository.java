package com.example.repository;

import com.example.entity.ArticleTagsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleTagsRepository extends CrudRepository<ArticleTagsEntity,Integer> {
    @Query("select a.tagId from ArticleTagsEntity as a where a.articleId=:articleId")
    List<Integer> getAllByArticleId(@Param("articleId") String articleId);

    @Transactional
    @Modifying
    @Query("delete from ArticleTagsEntity as a where a.articleId=:articleId and a.tagId=:tagId")
    int deleteByArticleIdAndTypeId(@Param("articleId") String articleId,
                                   @Param("tagId") Integer tagId);

    @Transactional
    @Modifying
    @Query("delete from ArticleTagsEntity as a where a.articleId=:articleId")
    int deleteByArticleId(@Param("articleId") String articleId);

}
