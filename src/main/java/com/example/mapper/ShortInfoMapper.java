package com.example.mapper;

import java.time.LocalDateTime;

public interface ShortInfoMapper {

    String getId();
    String getTitle();
    String getDescription();
    String getImageId();
    LocalDateTime getPublishedDate();
}
