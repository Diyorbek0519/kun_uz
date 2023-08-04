package com.example.service;

import com.example.dto.SmsDTO;
import com.example.dto.SmsHistoryDTO;
import com.example.entity.SmsHistoryEntity;
import com.example.exp.AppBadRequestException;
import com.example.repository.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmsHistoryService {
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;
    public void save(String message,String phone){
        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setPhone(phone);
        entity.setMessage(message);
        smsHistoryRepository.save(entity);
    }
    public List<SmsHistoryDTO> getByPhone(String phone){
        List<SmsHistoryEntity> entities =smsHistoryRepository.getByPhoneOrderByCreatedDateDesc(phone);
        if(entities.isEmpty()){
            throw new AppBadRequestException("phone not found");
        }
       return entities.stream().map(this::toDTO).collect(Collectors.toList());

    }
    public SmsHistoryDTO toDTO(SmsHistoryEntity entity){
        SmsHistoryDTO dto = new SmsHistoryDTO();
        dto.setId(entity.getId());
        dto.setPhone(entity.getPhone());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
