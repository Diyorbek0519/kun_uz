package com.example.repository;

import com.example.entity.SmsHistoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity,Integer> {
    List<SmsHistoryEntity> getByPhoneOrderByCreatedDateDesc(String phone);
}
