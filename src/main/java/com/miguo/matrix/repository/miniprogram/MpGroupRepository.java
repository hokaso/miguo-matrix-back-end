package com.miguo.matrix.repository.miniprogram;

import com.miguo.matrix.entity.miniprogram.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface MpGroupRepository extends JpaRepository<Group,String> {

    // 软删除某一个投票对象
    @Modifying
    @Transactional
    @Query(value = "update vote_groups set is_del = true , update_at = :#{#date},update_by = :#{#updateBy} where id = :#{#id} ", nativeQuery = true)
    void deleteById(String id, Date date, String updateBy);

    // 查找所有已被软删除的投票对象
    @Query(value = "select * from vote_groups where is_del = true",nativeQuery = true)
    Page<Group> findAllDeletedGroup(Pageable pageable);

    // 查找所有未被软删除的投票对象
    @Query(value = "select * from vote_groups where is_del = false",nativeQuery = true)
    Page<Group> findAllExistGroup(Pageable pageable);

    // 通过id找投票对象
    Group findGroupById(String id);
}
