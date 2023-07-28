package com.example.service;

import com.example.entity.ArticleEntity;
import com.example.entity.ArticleTypesEntity;
import com.example.exp.ItemNotFound;
import com.example.repository.ArticleTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypesService {
    @Autowired
    private ArticleTypesRepository articleTypesRepository;
    public void create(String articleId, List<Integer> typesList){
        typesList.forEach(t->{
            create(articleId,t);
        });
    }
    public void create(String articleId,Integer typeId){
        ArticleTypesEntity entity = new ArticleTypesEntity();
        entity.setArticleId(articleId);
        entity.setArticleTypeId(typeId);
        articleTypesRepository.save(entity);
    }
    public void update(String articleId,List<Integer> newtypesList){
        List<Integer> oldTypes=articleTypesRepository.getAllByArticleId(articleId);
        if(newtypesList==null){
            throw new ItemNotFound("new type list is null");
        }
        for (Integer typeId: newtypesList){
            if(!oldTypes.contains(typeId)){
                create(articleId,typeId);
            }
        }

        for (Integer typeId: oldTypes){
            if(!newtypesList.contains(typeId)){
                articleTypesRepository.deleteByArticleIdAndTypeId(articleId,typeId);
            }
        }
    }

    public void delete(String articleId) {
        articleTypesRepository.deleteByArticleId(articleId);
    }

    public void check(List<Integer> articleType) {

    }
}
