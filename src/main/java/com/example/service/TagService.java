package com.example.service;

import com.example.dto.TagDTO;
import com.example.entity.TagEntity;
import com.example.enums.Language;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFound;
import com.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ArticleTagsService articleTagsService;

    public Boolean create(Integer prtId,TagDTO tagDTO){
        if(tagDTO==null){
            throw new ItemNotFound("tagDTO is null");
        }
        TagEntity entity =new TagEntity();
        entity.setNameUz(tagDTO.getNameUz());
        entity.setNameRu(tagDTO.getNameRu());
        entity.setNameEn(tagDTO.getNameEn());
        entity.setPrtId(prtId);
        tagRepository.save(entity);
        return true;
    }

    public void checking(List<Integer> tags) {
        tags.forEach(t->{
            Optional<TagEntity> optional=tagRepository.findById(t);
            if(optional.isEmpty()){
                throw new AppBadRequestException("given tag not found");
            }
        });
    }
    public List<TagDTO> getTagList(String articleId, Language language){
        List<Integer> tagId=articleTagsService.getListTagIdByArticleId(articleId);
        List<TagDTO> tagDTOS=new LinkedList<>();
        tagId.forEach(id->{
            tagDTOS.add(new TagDTO(tagRepository.getTag(id,language.toString()).getName()));
        });
        return tagDTOS;
    }
}
