package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFound;
import com.example.repository.CustomProfileRepository;
import com.example.repository.ProfileRepository;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CustomProfileRepository customProfileRepository;
    public ProfileDTO create(ProfileDTO dto,Integer prtId){
        check(dto);
        Optional<ProfileEntity> optional=profileRepository.findByPhone(dto.getPhone());
        if(optional.isPresent()){
            throw new AppBadRequestException("Phone already exist");
        }
        Optional<ProfileEntity> optional1=profileRepository.findByEmail(dto.getEmail());
        if(optional1.isPresent()){
            throw new AppBadRequestException("Email  already exist");
        }
        ProfileEntity profileEntity =new ProfileEntity();
        profileEntity.setName(dto.getName());
        profileEntity.setSurname(dto.getSurname());
        profileEntity.setEmail(dto.getEmail());
        profileEntity.setPhone(dto.getPhone());
        profileEntity.setPassword(MD5Util.encode(dto.getPassword()));
        profileEntity.setStatus(ProfileStatus.ACTIVE);
        profileEntity.setRole(dto.getRole());
        profileEntity.setPrtId(prtId);
        profileRepository.save(profileEntity);
        dto.setCreateDate(profileEntity.getCreatedDate());
        dto.setId(profileEntity.getId());
        return dto;
    }
    public Boolean check(ProfileDTO profileDTO){
       if(profileDTO.getName().isBlank() || profileDTO.getSurname().isBlank() ||
               profileDTO.getEmail().isBlank() ||
               profileDTO.getPhone().isBlank()){
           throw new AppBadRequestException("Something  is null");
       }
       if(profileDTO.getPhone().length()!=13 || !profileDTO.getPhone().startsWith("+998")){
           throw new AppBadRequestException("phone number is wrong");
       }
       return true;
    }
    public Boolean update(Integer id, ProfileDTO dto){
        check(dto);
       Optional<ProfileEntity> optional =profileRepository.findById(id);
        if(optional.isEmpty()){
            throw new ItemNotFound("profile not found");
        }
        ProfileEntity profileEntity =optional.get();
        profileEntity.setName(dto.getName());
        profileEntity.setSurname(dto.getSurname());
        profileEntity.setEmail(dto.getEmail());
        profileEntity.setPhone(dto.getPhone());
        profileRepository.save(profileEntity);
        return true;
    }
    public Boolean updateDetail(Integer id, ProfileDTO dto){
        Optional<ProfileEntity> optional =profileRepository.findById(id);
        if(optional.isEmpty() || !optional.get().getVisible()){
            throw new ItemNotFound("profile not found");
        }
        ProfileEntity profileEntity =optional.get();
        if(dto.getName()==null && dto.getSurname()==null){
            throw new AppBadRequestException("Items is null");
        }
        if(dto.getName()==null && dto.getSurname()!=null){
            profileEntity.setSurname(dto.getSurname());
            profileRepository.save(profileEntity);
            return true;
        }
        if(dto.getName()!=null && dto.getSurname()==null){
            profileEntity.setName(dto.getName());
            profileRepository.save(profileEntity);
            return true;
        }
        profileEntity.setName(dto.getName());
        profileEntity.setSurname(dto.getSurname());
        profileRepository.save(profileEntity);
        return true;
    }
    public PageImpl<ProfileDTO> profileList(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<ProfileEntity> pageObj=profileRepository.findAll(pageable);
        List<ProfileDTO> profileDTOList= pageObj.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(profileDTOList,pageable,pageObj.getTotalElements());
    }
    public ProfileDTO toDTO(ProfileEntity entity){
        ProfileDTO dto = new ProfileDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());
        dto.setCreateDate(entity.getCreatedDate());
        return dto;
    }
    public Boolean delete(Integer id){
       /* Optional<ProfileEntity> optional=profileRepository.findById(id);
        if(optional.isEmpty()){
            throw new ItemNotFound("profile not found");
        }
        if(!optional.get().getVisible()){
            throw new AppBadRequestException("this profile alredy deleted");
        }
        ProfileEntity entity= optional.get();
        entity.setVisible(false);
        profileRepository.save(entity);
        return true;*/
        int n= profileRepository.delete(id);
        return n==1;
    }
    public List<ProfileDTO> filter(ProfileFilterDTO filterDTO){
        if(filterDTO==null){
            throw new AppBadRequestException("filterDto is empty");
        }
        List<ProfileEntity> entityList=customProfileRepository.filter(filterDTO);
        if(entityList.isEmpty()){
            throw new AppBadRequestException("Profiles not found");
        }
        List<ProfileDTO> dtoList = new LinkedList<>();
        entityList.forEach(t->{
            dtoList.add(toDTO(t));
        });
        return dtoList;
    }






}
