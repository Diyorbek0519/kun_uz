package com.example.service;

import com.example.dto.SmsDTO;
import com.example.exp.AppBadRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
public class SmsSenderService {
    @Autowired
    private SmsHistoryService smsHistoryService;
    @Value("${company.jwt}")
    private String jwt;
    @Value("${create.sms.url}")
    private String url;

    public SmsDTO sendSms(String phone) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String headerValue="Bearer "+jwt;
        headers.set("Authorization",headerValue);
        SmsDTO smsDTO = new SmsDTO();
        smsDTO.setPhone(phone);
        Random random = new Random();
        int message=random.nextInt(1000,9999);
        smsDTO.setMessage(String.valueOf(message));
        String json;
        try {
           json=new ObjectMapper().writeValueAsString(smsDTO);
        }catch (JsonProcessingException e){
            throw new AppBadRequestException("error");
        }
        HttpEntity entity=new HttpEntity(json,headers);
        RestTemplate restTemplate= new RestTemplate();
        SmsDTO response= restTemplate.exchange(url, HttpMethod.POST,entity,SmsDTO.class).getBody();
        smsHistoryService.save(String.valueOf(message),phone);
        return response;
    }
}
