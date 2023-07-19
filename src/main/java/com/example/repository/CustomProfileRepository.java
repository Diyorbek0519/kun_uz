package com.example.repository;

import com.example.dto.ProfileFilterDTO;
import com.example.entity.ProfileEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomProfileRepository {
    @Autowired
    protected EntityManager entityManager;
    public List<ProfileEntity> filter(ProfileFilterDTO filterDTO){
      StringBuilder stringBuilder = new StringBuilder("select s from ProfileEntity as s where s.visible=true");
        Map<String, Object> params = new HashMap<>();
        if(filterDTO.getName()!=null){
            stringBuilder.append(" and s.name=:name");
            params.put("name",filterDTO.getName());
        }
        if(filterDTO.getSurname()!=null){
            stringBuilder.append(" and s.surname=:surname");
            params.put("surname",filterDTO.getSurname());
        }
        if(filterDTO.getPhone()!=null){
            stringBuilder.append(" and s.phone=:phone");
            params.put("phone",filterDTO.getPhone());
        }
        if(filterDTO.getRole()!=null){
            stringBuilder.append(" and s.role=:role");
            params.put("role",filterDTO.getRole());
        }
        if(filterDTO.getCreatedDateFrom()!=null){
            stringBuilder.append(" and s.createdDate>=:createdDateFrom");
            params.put("createdDateFrom", LocalDateTime.of(filterDTO.getCreatedDateFrom(), LocalTime.MIN));
        }
        if(filterDTO.getCreatedDateTo()!=null){
            stringBuilder.append(" and s.createdDate<=:createdDateTo");
            params.put("createdDateTo",LocalDateTime.of(filterDTO.getCreatedDateTo(),LocalTime.MAX));
        }
        Query query=entityManager.createQuery(stringBuilder.toString());
        for (Map.Entry<String,Object> param: params.entrySet()){
            query.setParameter(param.getKey(),param.getValue());
        }
        List<ProfileEntity> entityList=query.getResultList();
        return entityList;

    }
}
