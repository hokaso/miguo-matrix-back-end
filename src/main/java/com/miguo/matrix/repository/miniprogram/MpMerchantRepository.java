package com.miguo.matrix.repository.miniprogram;

import com.miguo.matrix.entity.miniprogram.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Hocassian
 */
@Repository
public interface MpMerchantRepository extends JpaRepository<Merchant,String> {

    /**
     * 分页查找所有标题或者内容包含该关键字且未被软删除的活动（录入活动页面用,「keywords」为空时返回所有）
     * @param keywords
     * @param pageable
     * @return
     */
    @Query(value = "select * from vote_merchants WHERE merchant_name LIKE %:#{#keywords}% OR merchant_info LIKE %:#{#keywords}%",nativeQuery = true)
    Page<Merchant> findMerchantByKeywords(String keywords, Pageable pageable);

    /**
     * 通过id找投票对象
     * @param id
     * @return
     */
    Merchant findMerchantById(String id);
}
