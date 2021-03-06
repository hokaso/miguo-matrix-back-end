package com.miguo.matrix.repository.client;

import com.miguo.matrix.entity.client.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Hocassian
 */
@Repository
public interface VideoRepository extends JpaRepository<Video,String> {

    /**
     * ※客户方法
     *
     * 分页返回视频标题或内容包含某关键字且未被下架的视频条目（客户使用）
     * @param keywords
     * @param pageable
     * @return
     */
    @Query(value = "select * from client_video WHERE ( video_title LIKE %:#{#keywords}% OR video_profile LIKE %:#{#keywords}% ) and is_del = false and video_status = 'reviewed' order by create_at desc", nativeQuery = true)
    Page<Video> findVideoByKeywords(String keywords, Pageable pageable);

    /**
     * ※员工方法：进入某一篇视频修改时使用
     *
     * 通过id找视频
     * @param id
     * @return
     */
    Video findVideoById(String id);

    /**
     * ※员工方法
     *
     * 分页返回视频标题或内容包含某关键字的视频条目，当关键字为空时按更新时间顺序返回所有
     * @param keywords
     * @param pageable
     * @return
     */
    @Query(value = "select * from client_video WHERE video_title LIKE %:#{#keywords}% OR video_profile LIKE %:#{#keywords}%", nativeQuery = true)
    Page<Video> staffFindVideoByKeywords(String keywords, Pageable pageable);

    /**
     * ※审核方法：该方法与网络安全有关，与业务需求无关，未来迭代版本中使用
     *
     * 通过把「is_del」改为「true」来下架多个视频（并非删除）
     * @param id
     * @param date
     * @param updateBy
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update client_video set is_del = true , update_at = :#{#date},update_by = :#{#updateBy} where id = :#{#id} ", nativeQuery = true)
    void deleteSomeById(String id, Date date, String updateBy);

    /**
     * ※审核方法
     *
     * 分页不分类返回所有的视频条目
     * @param pageable
     * @param keywords
     * @return
     */
    @Query(value = "select * from client_video where ( video_title LIKE %:#{#keywords}% OR video_profile LIKE %:#{#keywords}% ) and video_status != 'draft'", nativeQuery = true)
    Page<Video> findAllExistVideo(String keywords, Pageable pageable);

    /**
     * ※审核方法
     *
     * 分页分类返回所有的视频条目
     * @param pageable
     * @param active
     * @param keywords
     * @return
     */
    @Query(value = "select * from client_video where ( video_title LIKE %:#{#keywords}% OR video_profile LIKE %:#{#keywords}% ) and is_del = :active and video_status != 'draft'",nativeQuery = true)
    Page<Video> findAllClassVideo(String keywords, Pageable pageable, Boolean active);
}
