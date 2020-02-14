package com.miguo.matrix.repository.covid;

import com.miguo.matrix.entity.covid.StaffList;
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
public interface MpListRepository extends JpaRepository<StaffList,String> {

    /**
     * 分页查找所有标题或者内容包含该关键字且未被软删除的活动（录入活动页面用,「keywords」为空时返回所有）
     * @param keywords
     * @param pageable
     * @return
     */
    @Query(value = "select * from east_staff_list WHERE east_name LIKE %:#{#keywords}%",nativeQuery = true)
    Page<StaffList> findListByKeywords(String keywords, Pageable pageable);

    /**
     * 通过id找活动
     * @param id
     * @return
     */
    StaffList findListById(String id);
}
