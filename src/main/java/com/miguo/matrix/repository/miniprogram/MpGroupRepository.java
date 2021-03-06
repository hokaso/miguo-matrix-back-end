package com.miguo.matrix.repository.miniprogram;

import com.miguo.matrix.entity.miniprogram.Group;
import com.miguo.matrix.vo.miniprogram.GroupVo;
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
public interface MpGroupRepository extends JpaRepository<Group,String> {

    /**
     * 分页查找所有标题或者内容包含该关键字且未被软删除的活动（录入活动页面用,「keywords」为空时返回所有）
     * @param keywords
     * @param pageable
     * @return
     */
    @Query("SELECT new com.miguo.matrix.vo.miniprogram.GroupVo(" +
            "g.id," +
            "g.createBy," +
            "g.updateBy," +
            "g.createAt," +
            "g.updateAt," +
            "g.isDel," +
            "g.activityId," +
            "g.groupName," +
            "g.groupProfile," +
            "g.groupVotes," +
            "g.groupPic," +
            "g.groupPicHd," +
            "a.activityName) " +
            "FROM Group g " +
            "LEFT JOIN Activity a ON g.activityId=a.id WHERE g.groupName LIKE %:#{#keywords}% OR g.groupProfile LIKE %:#{#keywords}%")
    Page<GroupVo> findGroupByKeywords(String keywords, Pageable pageable);

    /**
     * 查找所有标题或者内容包含该关键字的活动（不分页，输入活动相关信息时联结用）
     * @param keywords
     * @return
     */
    @Query(value = "select * from vote_groups WHERE group_name LIKE %:#{#keywords}% OR group_profile LIKE %:#{#keywords}%",nativeQuery = true)
    List<Group> findGroupByKeywordsFromInput(String keywords);

    /**
     * 通过id找投票对象
     * @param id
     * @return
     */
    Group findGroupById(String id);

    /**
     * 通过id找激活的投票对象序列
     * @param id
     * @return
     */
    @Query(value = "select * from vote_groups WHERE activity_id = :#{#id}", nativeQuery = true)
    List<Group> findActiveGroup(String id);

    /**
     * 通过id找激活的投票对象序列（排行榜）
     * @param id
     * @return
     */
    @Query(value = "select * from vote_groups WHERE activity_id = :#{#id} order by group_votes DESC limit 3", nativeQuery = true)
    List<Group> findRankGroup(String id);

    /**
     * 通过id定位投票对象，然后实现投票数的自增（+1）
     * @param id
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update `vote_groups` set group_votes=group_votes+1 where id = :#{#id}", nativeQuery = true)
    void voteForGroup(String id);
}
