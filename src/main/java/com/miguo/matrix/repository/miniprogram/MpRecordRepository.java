package com.miguo.matrix.repository.miniprogram;

import com.miguo.matrix.entity.miniprogram.Record;
import com.miguo.matrix.vo.miniprogram.RecordVo;
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
public interface MpRecordRepository extends JpaRepository<Record,String> {

    /**
     * 分页查找所有标题或者内容包含该关键字且未被软删除的活动（录入活动页面用,「keywords」为空时返回所有）
     * @param keywords
     * @param pageable
     * @return
     */
    @Query("SELECT new com.miguo.matrix.vo.miniprogram.RecordVo(" +
            "r.id," +
            "r.createBy," +
            "r.updateBy," +
            "r.createAt," +
            "r.updateAt," +
            "r.isDel," +
            "r.activityId," +
            "r.groupId," +
            "r.recordNickname," +
            "r.recordOpenid," +
            "r.recordDate," +
            "g.groupName) " +
            "FROM Record r " +
            "LEFT JOIN Group g ON r.groupId=g.id " +
            "WHERE r.recordNickname LIKE %:#{#keywords}%")
    Page<RecordVo> findRecordByKeywords(String keywords, Pageable pageable);

    /**
     * 通过id找投票记录
     * @param id
     * @return
     */
    Record findRecordById(String id);

    /**
     * 通过openid确定这个用户投过票没
     * @param openid
     * @return
     */
    @Query(value = "SELECT case when record_openid = :openid then 1 else 0 end FROM vote_records", nativeQuery = true)
    String findIsVoted(String openid);


}
