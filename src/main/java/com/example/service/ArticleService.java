package com.example.service;

import com.example.dto.ArticleDTO;
import com.example.dto.AttachDTO;
import com.example.dto.ProfileDTO;
import com.example.entity.ArticleEntity;
import com.example.entity.AttachEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ArticleStatus;
import com.example.enums.Language;
import com.example.exp.AppBadRequestException;
import com.example.exp.AppMethodNotAllowedException;
import com.example.exp.ItemNotFound;
import com.example.mapper.ArticleFullInfoIMapper;
import com.example.mapper.ShortInfoMapper;
import com.example.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ArticleTypesService articleTypesService;
    @Autowired
    private ArticleTagsService articleTagsService;
    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private RegionService regionService;
    @Autowired CategoryService categoryService;

    public ArticleDTO create(Integer moderatorId, ArticleDTO dto) {
        check(dto);
        ArticleEntity entity = new ArticleEntity();
        entity.setModeratorId(moderatorId);
        entity.setViewCount(dto.getViewCount());
        entity.setTitle(dto.getTitle());
        entity.setDescription((dto.getDescription()));
        entity.setContent(dto.getContent());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setImageId(dto.getImageId());
        articleTypeService.checking(dto.getArticleType());//check to yes or no to database
        tagService.checking(dto.getTags());//check to yes or no to database
        articleRepository.save(entity);
        articleTypesService.create(entity.getId(), dto.getArticleType());
        articleTagsService.create(entity.getId(), dto.getTags());
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
        if (entity == null || entity.getModeratorId() != moderatorId) {
            throw new AppMethodNotAllowedException();
        }
        if (dto.getStatus() == ArticleStatus.PUBLISHED) {
            throw new AppMethodNotAllowedException();
        }
        String oldImageId = entity.getImageId();
        boolean deleteImageTemp = false;
        if (dto.getImageId() != null) {
            entity.setImageId(dto.getImageId());
            deleteImageTemp = true;
        }
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleTypesService.update(dto.getId(), dto.getArticleType());
        articleTagsService.update(dto.getId(), dto.getTags());
        articleRepository.save(entity);
        if (deleteImageTemp) {
            attachService.delete(oldImageId);
        }
        return true;
    }

    public Boolean delete(Integer moderatorId, String articleId) {
        Optional<ArticleEntity> optional = articleRepository.findById(articleId);
        if (optional.isEmpty()) {
            throw new AppMethodNotAllowedException();
        }
        ArticleEntity entity = optional.get();
        if (entity == null || entity.getModeratorId() != moderatorId) {
            throw new AppMethodNotAllowedException();
        }
        articleTypesService.delete(articleId);
        articleTagsService.delete(articleId);
        articleRepository.deleteById(articleId);
        attachService.delete(entity.getImageId());
        return true;
    }

    public Boolean changeStatus(Integer publisherId, String articleId) {
        Optional<ArticleEntity> optional = articleRepository.findById(articleId);
        if (optional.isEmpty()) {
            throw new AppMethodNotAllowedException();
        }
        ArticleEntity entity = optional.get();
        entity.setPublisherId(publisherId);
        entity.setPublishedDate(LocalDateTime.now());
        if (entity.getStatus() == ArticleStatus.NOT_PUBLISHED) {
            entity.setStatus(ArticleStatus.PUBLISHED);
        } else {
            entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        }
        articleRepository.save(entity);
        return true;
    }

    public List<ArticleDTO> getLastArticlesByTypes(Integer articleTypeId, int limit) {
        if (articleTypeId == null) {
            throw new AppBadRequestException("type is null");
        }
        List<ArticleEntity> entities = articleRepository.getLastArticlesByArticleTypeId(articleTypeId, limit);
        List<ArticleDTO> dtoList = new LinkedList<>();
        entities.forEach(t -> {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(t.getId());
            articleDTO.setTitle(t.getTitle());
            articleDTO.setDescription(t.getDescription());
            articleDTO.setCreatedDate(t.getPublishedDate());
            AttachDTO image = new AttachDTO();
            articleDTO.setImage(attachService.getAttachWithURL(t.getImageId()));
            articleDTO.setImage(image);
            dtoList.add(articleDTO);
        });
        return dtoList;
    }

    public List<ArticleDTO> getLast8ByGivenList(List<String> articleId) {
        if (articleId.isEmpty()) {
            throw new ItemNotFound("given list is null");
        }
        List<ShortInfoMapper> mappers = articleRepository.getLastWhereNotInList(articleId);
        List<ArticleDTO> dtoList = new LinkedList<>();
        mappers.forEach(t -> {
            dtoList.add(getShortInfo(t));
        });
        return dtoList;
    }
    public ArticleDTO getShortInfo(ShortInfoMapper mapper){
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(mapper.getId());
        articleDTO.setTitle(mapper.getTitle());
        articleDTO.setDescription(mapper.getDescription());
        articleDTO.setCreatedDate(mapper.getPublishedDate());
        articleDTO.setImage(attachService.getAttachWithURL(mapper.getImageId()));
        return articleDTO;
    }
    public ArticleDTO toShortInfo(ArticleEntity entity){
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(entity.getId());
        articleDTO.setTitle(entity.getTitle());
        articleDTO.setDescription(entity.getDescription());
        articleDTO.setCreatedDate(entity.getPublishedDate());
        articleDTO.setImage(attachService.getAttachWithURL(entity.getImageId()));
        return articleDTO;
    }
    public ArticleDTO getArticleByIdAndLang(String articleId, Language language){
          if(articleId==null || language==null){
              throw new AppBadRequestException("something is null");
          }
          Optional<ArticleEntity> optional =articleRepository.findById(articleId);
          if(optional.isEmpty()){
              throw new AppBadRequestException("article not found");
          }
         Language lan= Language.valueOf(language.name());
          if(lan==null){
              throw new AppBadRequestException("language is wrong");
          }
          ArticleFullInfoIMapper mapper =articleRepository.getArticleByIdAndLang(articleId);
          return getFullInfo(mapper,language);

    }
    public ArticleDTO getFullInfo(ArticleFullInfoIMapper mapper,Language language){
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(mapper.getId());
        articleDTO.setTitle(mapper.getTitle());
        articleDTO.setDescription(mapper.getDescription());
        articleDTO.setContent(mapper.getContent());
        articleDTO.setSharedCount(mapper.getSharedCount());
        articleDTO.setRegion(regionService.getRegionWithOrderNumberAndName(mapper.getRegionId(), language));
        articleDTO.setCategory(categoryService.getByLangAndId(mapper.getCategoryId(), language));
        articleDTO.setPublishedDate(mapper.getPublishedDate());
        articleDTO.setViewCount(mapper.getViewCount());
        articleDTO.setTagDTO(tagService.getTagList(mapper.getId(),language));
        return articleDTO;
    }
    public List<ArticleDTO> getLast4ByTypesAndExceptGivenArticle(List<Integer> typeId,String articleId){
        if(typeId.isEmpty() || articleId==null){
            throw new AppBadRequestException("something is null");
        }
       List<ShortInfoMapper> mappers=articleRepository.getLast4ArticlesByTypeExceptGivenArticle(articleId,typeId);
        List<ArticleDTO> dtoList =new LinkedList<>();
        mappers.forEach(t->{
            dtoList.add(getShortInfo(t));
        });
        return dtoList;
    }
    public ArticleDTO getMostReadArtcle(){
        ShortInfoMapper mapper=articleRepository.getMostRead();
        return getShortInfo(mapper);
    }
    public List<ArticleDTO> getLast4ArticlesByTagId(Integer tagId){
        if(tagId==null){
            throw new AppBadRequestException("tagId is null");
        }
        List<ArticleDTO> dtoList = new LinkedList<>();
        List<ShortInfoMapper> mappers=articleRepository.getLast4ArticleByTagId(tagId);
        mappers.forEach(t->{
            dtoList.add(getShortInfo(t));
        });
        return dtoList;
    }

    //sal ishi bor elentlarni distinict qilish kk
    public List<ArticleDTO> getLast5ArticlesByTypeAndRegion(Integer regionId, List<Integer> typeId){
        if(regionId==null || typeId.isEmpty()){
            throw new AppBadRequestException("tagId is null");
        }
        List<ArticleDTO> dtoList = new LinkedList<>();
        List<ShortInfoMapper> mappers=articleRepository.getLast5ArticleByTypeAndRegion(typeId,regionId);
        mappers.forEach(t->{
            dtoList.add(getShortInfo(t));
        });
        return dtoList;
    }
    public PageImpl<ArticleDTO> getArticleListByRegionId(Integer regionId, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        PageImpl<ArticleEntity> pageObj=articleRepository.getByRegionId(regionId,pageable);
        List<ArticleDTO> dtoList = pageObj.stream().map(this::toShortInfo).collect(Collectors.toList());
        return new PageImpl<>(dtoList,pageable,pageObj.getTotalElements());
    }
    public PageImpl<ArticleDTO> getArticleListByCategoryId(Integer categoryId, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        PageImpl<ArticleEntity> pageObj=articleRepository.getByRegionId(categoryId,pageable);
        List<ArticleDTO> dtoList = pageObj.stream().map(this::toShortInfo).collect(Collectors.toList());
        return new PageImpl<>(dtoList,pageable,pageObj.getTotalElements());
    }
    public List<ArticleDTO> getLast5ArticleByCt(Integer categoryId){
        List<ArticleEntity> entities=articleRepository.getTop5ByCategoryIdOrderByPublishedDate(categoryId);
        List<ArticleDTO> dtoList=new LinkedList<>();
        entities.forEach(t->{
            dtoList.add(toShortInfo(t));
        });
        return dtoList;
    }



}
