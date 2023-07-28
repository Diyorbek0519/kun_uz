package com.example.repository;

import com.example.entity.RegionEntity;
import com.example.mapper.RegionMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity,Integer> {
   @Transactional
    @Modifying
    @Query("update RegionEntity as s set s.orderNumber=:orderNumber, s.nameEn=:nameEn, s.nameRu=:nameRu, s.nameUz=:nameUz where s.id=:id")
    int update(@Param("id") Integer id,@Param("orderNumber") Integer orderNumber,
               @Param("nameUz") String nameUz,
               @Param("nameRu") String nameRu,
               @Param("nameEn") String nameEn);

   @Transactional
    @Modifying
    @Query("update RegionEntity as s set s.visible=false where s.id=:id ")
    int delete(@Param("id") Integer id);
   List<RegionEntity> getRegionEntitiesByVisibleTrue();

    @Query(value = "select id, order_number as orderNumber, " +
            "CASE :lang " +
            "   WHEN 'en' THEN name_en " +
            "   WHEN 'ru' THEN name_ru" +
            "   ELSE name_uz" +
            " END as name" +
            " from region where visible = true order by order_number", nativeQuery = true)
    List<RegionMapper> findAllByLang(@Param("lang") String lang);

    @Query(value = "select  order_number as orderNumber, " +
            "CASE :lang " +
            "   WHEN 'en' THEN name_en " +
            "   WHEN 'ru' THEN name_ru" +
            "   ELSE name_uz" +
            " END as name" +
            " from region where visible = true and id=:regionId", nativeQuery = true)
    RegionMapper findByLang(@Param("lang") String lang,
                            @Param("regionId") Integer regionId);


}
