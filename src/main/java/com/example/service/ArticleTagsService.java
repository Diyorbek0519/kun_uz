package com.example.service;

import com.example.entity.ArticleTagsEntity;
import com.example.exp.ItemNotFound;
import com.example.repository.ArticleTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTagsService {
   @Autowired
    private ArticleTagsRepository articleTagsRepository;
   public void create(String articleId, List<Integer> articletags){
       articletags.forEach(t->{
           create(articleId,t);
       });
   }
   public void create(String articleId,Integer articleTagId){
       ArticleTagsEntity entity = new ArticleTagsEntity();
       entity.setArticleId(articleId);
       entity.setTagId(articleTagId);
       articleTagsRepository.save(entity);
   }
    public void update(String articleId,List<Integer> newtypesList){
        List<Integer> oldTypes=articleTagsRepository.getAllByArticleId(articleId);
        if(newtypesList==null){
            throw new ItemNotFound("new tag list is null");
        }
        for (Integer tagId: newtypesList){
            if(!oldTypes.contains(tagId)){
                create(articleId,tagId);
            }
        }

        for (Integer typeId: oldTypes){
            if(!newtypesList.contains(typeId)){
                articleTagsRepository.deleteByArticleIdAndTypeId(articleId,typeId);
            }
        }
    }

    public void delete(String articleId) {
       articleTagsRepository.deleteByArticleId(articleId);
    }
    public List<Integer> getListTagIdByArticleId(String articleId){
       return articleTagsRepository.getAllByArticleId(articleId);
    }


}
