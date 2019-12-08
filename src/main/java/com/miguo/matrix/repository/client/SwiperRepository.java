package com.miguo.matrix.repository.client;

import com.miguo.matrix.entity.client.Swiper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface SwiperRepository extends JpaRepository<Swiper,String> {
    // 软删除某一张轮播图
    @Modifying
    @Transactional
    @Query(value = "update client_swiper set is_del = true , update_at = :#{#date},update_by = :#{#updateBy} where  id = :#{#id} ", nativeQuery = true)
    void deleteById(String id, Date date, String updateBy);
}
