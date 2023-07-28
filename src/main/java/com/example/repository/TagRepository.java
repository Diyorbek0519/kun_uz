package com.example.repository;

import com.example.entity.TagEntity;
import com.example.mapper.TagMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface TagRepository extends CrudRepository<TagEntity,Integer> {
    @Query(value ="select " +
            "case :lang" +
            " WHEN 'en' THEN name_en"+
            " WHEN 'ru' THEN name_ru"+
            " ELSE name_uz "+
            "END as name"+
            " from tag where visible=true and id=:tagId", nativeQuery = true )
    TagMapper getTag(@Param("tagId") Integer tagId,
                     @Param("lang") String lang);

}
