package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.entity.ArticleTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity,String> {


}
