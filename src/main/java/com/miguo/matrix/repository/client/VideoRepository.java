package com.miguo.matrix.repository.client;

import com.miguo.matrix.entity.client.Article;
import com.miguo.matrix.entity.client.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface VideoRepository extends JpaRepository<Video,String> {

    // 软删除某一视频
    @Modifying
    @Transactional
    @Query(value = "update client_video set is_del = true , update_at = :#{#date},update_by = :#{#updateBy} where  id = :#{#id} ", nativeQuery = true)
    void deleteById(String id, Date date, String updateBy);

    // 分页视频标题或者内容包含该关键字且未被软删除的文章
    @Query(value = "select * from client_video WHERE video_title LIKE %:#{#keywords}% OR video_profile LIKE %:#{#keywords}% and is_del = false",nativeQuery = true)
    Page<Video> findVideoByKeywords(String keywords,Pageable pageable);

    // 查找所有未被软删除的视频
    @Query(value = "select * from client_video where is_del = false",nativeQuery = true)
    Page<Video> findAllExistVideo(Pageable pageable);

    // 查找所有已被软删除的视频
    @Query(value = "select * from client_video where is_del = true",nativeQuery = true)
    Page<Video> findAllDeletedVideo(Pageable pageable);

    // 通过id找视频
    Video findVideoById(String id);
}
