package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.dto.RegionDTO;
import com.example.entity.CategoryEntity;
import com.example.entity.RegionEntity;
import com.example.enums.Language;
import com.example.exp.AppBadRequestException;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public CategoryDTO create(CategoryDTO categoryDTO){
        check(categoryDTO);
       CategoryEntity entity=new CategoryEntity();
        entity.setOrderNumber(categoryDTO.getOrderNumber());
        entity.setNameEn(categoryDTO.getNameEn());
        entity.setNameRu(categoryDTO.getNameRu());
        entity.setNameUz(categoryDTO.getNameUz());
        categoryRepository.save(entity);
        categoryDTO.setId(entity.getId());
        return categoryDTO;
    }
    public Boolean check(CategoryDTO dto) {
        if (dto.getOrderNumber() == null || dto.getNameUz() == null || dto.getNameRu() == null || dto.getNameEn() == null) {
            throw new AppBadRequestException("Something is null");
        }
        return true;
    }
    public Boolean update(Integer id,CategoryDTO dto){
        int n= categoryRepository.update(id,dto.getOrderNumber(), dto.getNameUz(), dto.getNameRu(), dto.getNameEn());
        return n==1;
    }
    public Boolean delete(Integer id){
        int n= categoryRepository.delete(id);
        return n==1;
    }
    public List<CategoryDTO> getAll(){
        List<CategoryEntity> entityList =categoryRepository.getCategoryEntitiesByVisibleTrue();
        List<CategoryDTO> dtoList = new LinkedList<>();
        entityList.forEach(t->{
            dtoList.add(toDTO(t));
        });
        return dtoList;

    }
    public CategoryDTO toDTO(CategoryEntity entity){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(entity.getId());
        categoryDTO.setOrderNumber(entity.getOrderNumber());
        categoryDTO.setCreatedDate(entity.getCreatedDate());
        categoryDTO.setNameUz(entity.getNameUz());
        categoryDTO.setNameRu(entity.getNameRu());
        categoryDTO.setNameEn(entity.getNameEn());
        return categoryDTO;
    }
    public List<CategoryDTO> getByLang(Language lang){
        List<CategoryDTO> dtoList = new LinkedList<>();
        categoryRepository.findAllByLang(lang.name()).forEach(mapper -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(mapper.getId());
            dto.setOrderNumber(mapper.getOrderNumber());
            dto.setName(mapper.getName());
            dtoList.add(dto);
        });
        return dtoList;
    }

}
