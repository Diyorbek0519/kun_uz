package com.example.service;

import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.enums.Language;
import com.example.exp.AppBadRequestException;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionDTO regionDTO) {
        check(regionDTO);
        RegionEntity entity=new RegionEntity();
        entity.setOrderNumber(regionDTO.getOrderNumber());
        entity.setNameEn(regionDTO.getNameEn());
        entity.setNameRu(regionDTO.getNameRu());
        entity.setNameUz(regionDTO.getNameUz());
        regionRepository.save(entity);
        regionDTO.setId(entity.getId());
        return regionDTO;
    }

    public Boolean check(RegionDTO dto) {
        if (dto.getOrderNumber() == null || dto.getNameUz() == null || dto.getNameRu() == null || dto.getNameEn() == null) {
            throw new AppBadRequestException("Something is null");
        }
        return true;
    }
    public Boolean update(Integer id,RegionDTO dto){
       int n= regionRepository.update(id,dto.getOrderNumber(), dto.getNameUz(), dto.getNameRu(), dto.getNameEn());
       return n==1;
    }
    public Boolean delete(Integer id){
        int n= regionRepository.delete(id);
        return n==1;
    }
    public List<RegionDTO> getAll(){
        List<RegionEntity> entityList =regionRepository.getRegionEntitiesByVisibleTrue();
        List<RegionDTO> dtoList = new LinkedList<>();
        entityList.forEach(t->{
            dtoList.add(toDTO(t));
        });
        return dtoList;

    }
    public RegionDTO toDTO(RegionEntity entity){
        RegionDTO regionDTO =new RegionDTO();
        regionDTO.setId(entity.getId());
        regionDTO.setOrderNumber(entity.getOrderNumber());
        regionDTO.setCreatedDate(entity.getCreatedDate());
        regionDTO.setNameUz(entity.getNameUz());
        regionDTO.setNameRu(entity.getNameRu());
        regionDTO.setNameEn(entity.getNameEn());
        return regionDTO;
    }
    public List<RegionDTO> getByLang(Language lang){
        List<RegionDTO> dtoList = new LinkedList<>();
        regionRepository.findAllByLang(lang.name()).forEach(mapper -> {
            RegionDTO dto = new RegionDTO();
            dto.setId(mapper.getId());
            dto.setOrderNumber(mapper.getOrderNumber());
            dto.setName(mapper.getName());
            dtoList.add(dto);
        });
        return dtoList;
    }



}
