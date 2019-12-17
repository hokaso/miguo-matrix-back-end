package com.miguo.matrix.repository.miniprogram;

import com.miguo.matrix.entity.miniprogram.Swiper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface MpSwiperRepository extends JpaRepository<Swiper,String> {

    // 软删除某一张轮播图
    @Modifying
    @Transactional
    @Query(value = "update vote_swipers set is_del = true , update_at = :#{#date},update_by = :#{#updateBy} where id = :#{#id} ", nativeQuery = true)
    void deleteById(String id, Date date, String updateBy);

    // 查找所有已被软删除的轮播图
    @Query(value = "select * from vote_swipers where is_del = true",nativeQuery = true)
    Page<Swiper> findAllDeletedSwiper(Pageable pageable);

    // 查找所有未被软删除的轮播图
    @Query(value = "select * from vote_swipers where is_del = false",nativeQuery = true)
    Page<Swiper> findAllExistSwiper(Pageable pageable);

    // 通过id找轮播图
    Swiper findSwiperById(String id);
}
