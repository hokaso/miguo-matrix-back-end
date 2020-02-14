package com.miguo.matrix.repository.covid;

import com.miguo.matrix.entity.covid.StaffHealth;
import com.miguo.matrix.vo.covid.HealthVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Hocassian
 */
@Repository
public interface MpHealthRepository extends JpaRepository<StaffHealth,String> {

    /**
     * 分页查找所有标题或者内容包含该关键字且未被软删除的活动（录入活动页面用,「keywords」为空时返回所有）
     * @param keywords
     * @param pageable
     * @return
     */
    @Query("SELECT new com.miguo.matrix.vo.covid.HealthVo(" +
            "g.id," +
            "g.createBy," +
            "g.updateBy," +
            "g.createAt," +
            "g.updateAt," +
            "g.isDel," +
            "g.eastId," +
            "g.staffTemp," +
            "g.staffStatus," +
            "g.staffPlace," +
            "a.eastName) " +
            "FROM StaffHealth g " +
            "LEFT JOIN StaffList a ON g.eastId=a.id WHERE east_name LIKE %:#{#keywords}%")
    Page<HealthVo> findHealthByKeywords(String keywords, Pageable pageable);

    /**
     * 通过id找投票对象
     * @param id
     * @return
     */
    StaffHealth findHealthById(String id);
}
