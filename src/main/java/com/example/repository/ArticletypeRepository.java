package com.example.repository;

import com.example.entity.ArticleTypeEntity;
import com.example.mapper.ArticleTypeMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticletypeRepository extends CrudRepository<ArticleTypeEntity,Integer> {
    @Transactional
    @Modifying
    @Query("update ArticleTypeEntity as s set s.orderNumber=:orderNumber, s.nameEn=:nameEn, s.nameRu=:nameRu, s.nameUz=:nameUz where s.id=:id")
    int update(@Param("id") Integer id, @Param("orderNumber") Integer orderNumber,
               @Param("nameUz") String nameUz,
               @Param("nameRu") String nameRu,
               @Param("nameEn") String nameEn);

    @Transactional
    @Modifying
    @Query("update ArticleTypeEntity as s set s.visible=false where s.id=:id ")
    int delete(@Param("id") Integer id);
    List<ArticleTypeEntity> getArticleTypeEntitiesByVisibleTrue();

    @Query(value = "select id, order_number as orderNumber, " +
            "CASE :lang " +
            "   WHEN 'en' THEN name_en " +
            "   WHEN 'ru' THEN name_ru" +
            "   ELSE name_uz" +
            " END as name" +
            " from article_type where visible = true order by order_number", nativeQuery = true)
    List<ArticleTypeMapper> findAllByLang(@Param("lang") String lang);
}
