package com.miguo.matrix.repository.miniprogram;

import com.miguo.matrix.entity.miniprogram.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Hocassian
 */
@Repository
public interface MpActivityRepository extends JpaRepository<Activity,String> {

    // 软删除某一活动
    @Modifying
    @Transactional
    @Query(value = "update vote_activities set is_del = true , update_at = :#{#date},update_by = :#{#updateBy} where id = :#{#id} ", nativeQuery = true)
    void deleteById(String id, Date date, String updateBy);

    // 查找所有标题或者内容包含该关键字且未被软删除的活动（输入活动相关信息时联结用）
    @Query(value = "select * from vote_activities WHERE activity_name LIKE %:#{#keywords}% OR activity_profile LIKE %:#{#keywords}% and is_del = false",nativeQuery = true)
    List<Activity> findActivityByKeywordsFromInput(String keywords);

    // 分页查找所有标题或者内容包含该关键字且未被软删除的活动（录入活动页面用）
    @Query(value = "select * from vote_activities WHERE activity_name LIKE %:#{#keywords}% OR activity_profile LIKE %:#{#keywords}% and is_del = false",nativeQuery = true)
    Page<Activity> findActivityByKeywords(String keywords, Pageable pageable);

    // 查找所有未被软删除的活动
    @Query(value = "select * from vote_activities where is_del = false",nativeQuery = true)
    Page<Activity> findAllExistActivity(Pageable pageable);

    // 查找所有已被软删除的活动
    @Query(value = "select * from vote_activities where is_del = true",nativeQuery = true)
    Page<Activity> findAllDeletedActivity(Pageable pageable);

    // 通过id找活动
    Activity findActivityById(String id);
}
