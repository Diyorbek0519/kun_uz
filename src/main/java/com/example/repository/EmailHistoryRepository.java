package com.example.repository;

import com.example.entity.EmailHistoryEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity,Integer>, PagingAndSortingRepository<EmailHistoryEntity,Integer> {
    List<EmailHistoryEntity> getAllByEmail(String email);
    List<EmailHistoryEntity> getAllByCreatedDateBetween(LocalDateTime t1,LocalDateTime t2);


}
