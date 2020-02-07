package com.miguo.matrix.repository.miniprogram;

import com.miguo.matrix.entity.miniprogram.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hocassian
 */
@Repository
public interface MpActivityRepository extends JpaRepository<Activity,String> {

    /**
     * 查找所有标题或者内容包含该关键字的活动（不分页，输入活动相关信息时联结用）
     * @param keywords
     * @return
     */
    @Query(value = "select * from vote_activities WHERE activity_name LIKE %:#{#keywords}% OR activity_profile LIKE %:#{#keywords}%",nativeQuery = true)
    List<Activity> findActivityByKeywordsFromInput(String keywords);



    /**
     * 分页查找所有标题或者内容包含该关键字且未被软删除的活动（录入活动页面用,「keywords」为空时返回所有）
     * @param keywords
     * @param pageable
     * @return
     */
    @Query(value = "select * from vote_activities WHERE activity_name LIKE %:#{#keywords}% OR activity_profile LIKE %:#{#keywords}%",nativeQuery = true)
    Page<Activity> findActivityByKeywords(String keywords, Pageable pageable);

    /**
     * 通过id找活动
     * @param id
     * @return
     */
    Activity findActivityById(String id);
}
