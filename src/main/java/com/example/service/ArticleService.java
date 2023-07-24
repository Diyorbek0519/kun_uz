package com.example.service;

import com.example.dto.ArticleDTO;
import com.example.dto.ArticleTypeDTO;
import com.example.entity.*;
import com.example.enums.ArticleStatus;
import com.example.exp.AppBadRequestException;
import com.example.exp.AppMethodNotAllowedException;
import com.example.repository.ArticleRepository;
import com.example.repository.AttachRepository;
import com.example.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private AttachService attachService;

    public ArticleDTO create(Integer moderatorId, ArticleDTO dto) {
        check(dto);
        ArticleEntity entity = new ArticleEntity();

        ProfileEntity moderatorEntity = new ProfileEntity();
        moderatorEntity.setId(moderatorId);
        entity.setModeratorId(moderatorEntity);

        entity.setViewCount(dto.getViewCount());
        entity.setSharedCount(dto.getSharedCount());
        entity.setTitle(dto.getTitle());
        entity.setDescription((dto.getDescription()));
        entity.setContent(dto.getContent());

        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(dto.getRegionId());
        entity.setRegionId(regionEntity);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(dto.getCategoryId());
        entity.setCategoryId(categoryEntity);

        AttachEntity attachEntity = new AttachEntity();
        attachEntity.setId(dto.getImageId());
        entity.setAttachEntity(attachEntity);

        List<Integer> articleTypeEntityList = dto.getArticleType();
        List<ArticleTypeEntity> articleTypeEntities = new LinkedList<>();
        articleTypeEntityList.forEach(temp -> {
            ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();
            articleTypeEntity.setId(temp);
            articleTypeEntities.add(articleTypeEntity);
        });
        entity.setArticleTypes(articleTypeEntities);

        articleRepository.save(entity);
        dto.setId(entity.getId());
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean check(ArticleDTO dto) {
        if (dto.getTitle() == null || dto.getDescription() == null || dto.getContent() == null ||
                dto.getRegionId() == null || dto.getCategoryId() == null) {
            throw new AppBadRequestException("Something is null");
        }
        return true;
    }

    public Boolean update(Integer moderatorId, ArticleDTO dto) {
        if (dto == null) {
            throw new AppBadRequestException("dto is null");
        }
        Optional<ArticleEntity> optional = articleRepository.findById(dto.getId());
        if (optional.isEmpty()) {
            throw new AppMethodNotAllowedException();
        }
        ArticleEntity entity = optional.get();
        if (entity == null || entity.getModeratorId().getId() != moderatorId) {
            throw new AppMethodNotAllowedException();
        }
        if (dto.getStatus() == ArticleStatus.PUBLISHED) {
            throw new AppMethodNotAllowedException();
        }
        AttachEntity attachEntity = entity.getAttachEntity();
        boolean deleteImageTemp = false;
        if (dto.getImageId() != null) {
            AttachEntity attach = new AttachEntity();
            attach.setId(dto.getImageId());
            entity.setAttachEntity(attach);
            deleteImageTemp=true;
        }
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());


        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(dto.getRegionId());
        entity.setRegionId(regionEntity);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(dto.getCategoryId());
        entity.setCategoryId(categoryEntity);

        articleRepository.save(entity);
        if (deleteImageTemp) {
            attachService.delete(attachEntity.getId());
        }
        return true;
    }

    public Boolean delete(Integer moderatorId, String articleId) {
        Optional<ArticleEntity> optional = articleRepository.findById(articleId);
        if (optional.isEmpty()) {
            throw new AppMethodNotAllowedException();
        }
        ArticleEntity entity = optional.get();
        if (entity == null || entity.getModeratorId().getId() != moderatorId) {
            throw new AppMethodNotAllowedException();
        }
        articleRepository.deleteById(articleId);
        attachService.delete(entity.getAttachEntity().getId());
        return true;
    }
    public Boolean changeStatus(Integer publisherId, String articleId){
        Optional<ArticleEntity> optional =articleRepository.findById(articleId);
        if (optional.isEmpty()) {
            throw new AppMethodNotAllowedException();
        }
        ArticleEntity entity = optional.get();

        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId(publisherId);
        entity.setPublisherId(profileEntity);

        entity.setPublishedDate(LocalDateTime.now());
        if(entity.getStatus()==null){
            entity.setStatus(ArticleStatus.PUBLISHED);
        }else{
            entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        }
        articleRepository.save(entity);
        return true;
    }
    public List<ArticleDTO> getLast5ArticlesByTypes(String articleType){
        if(articleType==null){
            throw new AppBadRequestException("type is null");
        }
  return null;
    }


}
