package com.example.mapper;

import java.time.LocalDateTime;

public interface ArticleFullInfoIMapper {

    /*id(uuid),title,description,content,shared_count,
    region(key,name),category(key,name),published_date,view_count,like_count,
    tagList(name)
*/
        String getId();
        String getTitle();
        String getDescription();
        String getContent();
        Integer getSharedCount();
        Integer getRegionId();
        Integer getCategoryId();
        LocalDateTime getPublishedDate();
        Integer getViewCount();
        Integer getLikeCount();


}
