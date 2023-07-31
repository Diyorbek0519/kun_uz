package com.example.service;

import com.example.dto.ArticleDTO;
import com.example.dto.EmailHistoryDTO;
import com.example.entity.EmailHistoryEntity;
import com.example.exp.AppBadRequestException;
import com.example.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;
    public void save(String message,String email){
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setEmail(email);
        entity.setMessage(message);
        emailHistoryRepository.save(entity);
    }
    public List<EmailHistoryDTO> getByEmail(String email){
        if(email==null){
            throw new AppBadRequestException("Email is null");
        }
        List<EmailHistoryEntity> entityList=emailHistoryRepository.getAllByEmail(email);
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }
    public List<EmailHistoryDTO> getByGivenDate(LocalDate date){
        LocalDateTime t1=LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime t2=LocalDateTime.of(date, LocalTime.MAX);
        List<EmailHistoryEntity> entityList=emailHistoryRepository.getAllByCreatedDateBetween(t1,t2);
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }
    public PageImpl<EmailHistoryDTO> pagination(int page, int size){
        Pageable pageable = PageRequest.of(page,size,Sort.by("createdDate").descending());
        Page<EmailHistoryEntity> pageObj= emailHistoryRepository.findAll(pageable);
        List<EmailHistoryDTO> dtoList = pageObj.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtoList,pageable,pageObj.getTotalElements());
    }


    public EmailHistoryDTO toDTO(EmailHistoryEntity entity){
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setMessage(entity.getMessage());
        return dto;
    }


}
