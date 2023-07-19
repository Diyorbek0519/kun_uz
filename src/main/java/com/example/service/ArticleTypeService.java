package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.CategoryDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.CategoryEntity;
import com.example.enums.Language;
import com.example.exp.AppBadRequestException;
import com.example.repository.ArticletypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticletypeRepository articletypeRepository;
    public ArticleTypeDTO create(ArticleTypeDTO articleTypeDTO){
        check(articleTypeDTO);
        ArticleTypeEntity entity=new ArticleTypeEntity();
        entity.setOrderNumber(articleTypeDTO.getOrderNumber());
        entity.setNameEn(articleTypeDTO.getNameEn());
        entity.setNameRu(articleTypeDTO.getNameRu());
        entity.setNameUz(articleTypeDTO.getNameUz());
        articletypeRepository.save(entity);
        articleTypeDTO.setId(entity.getId());
        return articleTypeDTO;
    }
    public Boolean check(ArticleTypeDTO dto) {
        if (dto.getOrderNumber() == null || dto.getNameUz() == null || dto.getNameRu() == null || dto.getNameEn() == null) {
            throw new AppBadRequestException("Something is null");
        }
        return true;
    }
    public Boolean update(Integer id,ArticleTypeDTO dto){
        int n= articletypeRepository.update(id,dto.getOrderNumber(), dto.getNameUz(), dto.getNameRu(), dto.getNameEn());
        return n==1;
    }
    public Boolean delete(Integer id){
        int n= articletypeRepository.delete(id);
        return n==1;
    }
    public List<ArticleTypeDTO> getAll(){
        List<ArticleTypeEntity> entityList =articletypeRepository.getArticleTypeEntitiesByVisibleTrue();
        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        entityList.forEach(t->{
            dtoList.add(toDTO(t));
        });
        return dtoList;

    }
    public ArticleTypeDTO toDTO(ArticleTypeEntity entity){
        ArticleTypeDTO articleTypeDTO = new ArticleTypeDTO();
        articleTypeDTO.setId(entity.getId());
        articleTypeDTO.setOrderNumber(entity.getOrderNumber());
        articleTypeDTO.setCreatedDate(entity.getCreatedDate());
        articleTypeDTO.setNameUz(entity.getNameUz());
        articleTypeDTO.setNameRu(entity.getNameRu());
        articleTypeDTO.setNameEn(entity.getNameEn());
        return articleTypeDTO;
    }
    public List<ArticleTypeDTO> getByLang(Language lang){
        List<ArticleTypeDTO> dtoList = new LinkedList<>();
        articletypeRepository.findAllByLang(lang.name()).forEach(mapper -> {
            ArticleTypeDTO dto = new ArticleTypeDTO();
            dto.setId(mapper.getId());
            dto.setOrderNumber(mapper.getOrderNumber());
            dto.setName(mapper.getName());
            dtoList.add(dto);
        });
        return dtoList;
    }
}
