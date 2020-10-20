package com.miguo.matrix.repository.miniprogram;

import com.miguo.matrix.entity.miniprogram.MpSwiper;
import com.miguo.matrix.vo.miniprogram.SwiperVo;
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
public interface MpSwiperRepository extends JpaRepository<MpSwiper,String> {

    /**
     * 分页查找所有标题或者内容包含该关键字且未被软删除的活动（录入活动页面用,「keywords」为空时返回所有）
     * @param keywords
     * @param pageable
     * @return
     */
    @Query("SELECT new com.miguo.matrix.vo.miniprogram.SwiperVo(" +
            "g.id," +
            "g.createBy," +
            "g.updateBy," +
            "g.createAt," +
            "g.updateAt," +
            "g.isDel," +
            "g.activityId," +
            "g.swiperName," +
            "g.swiperPic," +
            "a.activityName) " +
            "FROM MpSwiper g " +
            "LEFT JOIN Activity a ON g.activityId=a.id WHERE g.swiperName LIKE %:#{#keywords}%")
    Page<SwiperVo> findSwiperByKeywords(String keywords, Pageable pageable);

    /**
     * 通过id找轮播图
     * @param id
     * @return
     */
    MpSwiper findSwiperById(String id);

    /**
     * 通过id找激活的投票对象
     * @param id
     * @return
     */
    @Query(value = "select * from vote_swipers WHERE activity_id = :#{#id}",nativeQuery = true)
    List<MpSwiper> findActiveSwiper(String id);
}
