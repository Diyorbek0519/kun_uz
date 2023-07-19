package com.example.repository;

import com.example.entity.ProfileEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity,Integer>,
        PagingAndSortingRepository<ProfileEntity,Integer> {
    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByPhone(String phone);
    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible=false where id=:id")
    int delete(@Param("id") Integer id);


}
