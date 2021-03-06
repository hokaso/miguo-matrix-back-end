package com.miguo.matrix.repository.media;

import com.miguo.matrix.entity.media.MediaVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Hocassian
 */
@Repository
public interface MediaVideoRepository extends JpaRepository<MediaVideo,String> {

    /**
     * ※员工方法：进入某一篇视频修改时使用
     *
     * 通过id找视频
     * @param id
     * @return
     */
    MediaVideo findVideoById(String id);

    /**
     * ※员工方法
     *
     * 分页返回视频标题或内容包含某关键字的视频条目，当关键字为空时按更新时间顺序返回所有
     * @param keywords
     * @param pageable
     * @return
     */
    @Query(value = "select * from media_video WHERE video_title LIKE %:#{#keywords}% OR video_profile LIKE %:#{#keywords}%", nativeQuery = true)
    Page<MediaVideo> staffFindVideoByKeywords(String keywords, Pageable pageable);


    /**
     * ※审核方法
     *
     * 分页不分类返回所有的视频条目
     * @param pageable
     * @param keywords
     * @return
     */
    @Query(value = "select * from media_video where ( video_title LIKE %:#{#keywords}% OR video_profile LIKE %:#{#keywords}% ) and video_status != 'draft'", nativeQuery = true)
    Page<MediaVideo> findAllExistVideo(String keywords, Pageable pageable);

    /**
     * ※审核方法
     *
     * 分页分类返回所有的视频条目
     * @param pageable
     * @param active
     * @param keywords
     * @return
     */
    @Query(value = "select * from media_video where ( video_title LIKE %:#{#keywords}% OR video_profile LIKE %:#{#keywords}% ) and is_del = :active and video_status != 'draft'",nativeQuery = true)
    Page<MediaVideo> findAllClassVideo(String keywords, Pageable pageable, Boolean active);
}
