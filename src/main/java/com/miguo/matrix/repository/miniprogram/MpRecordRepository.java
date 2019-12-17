package com.miguo.matrix.repository.miniprogram;

import com.miguo.matrix.entity.miniprogram.Record;
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
public interface MpRecordRepository extends JpaRepository<Record,String> {

    // 软删除某一个投票对象
    @Modifying
    @Transactional
    @Query(value = "update vote_records set is_del = true , update_at = :#{#date},update_by = :#{#updateBy} where id = :#{#id} ", nativeQuery = true)
    void deleteById(String id, Date date, String updateBy);

    // 查找所有已被软删除的投票对象
    @Query(value = "select * from vote_records where is_del = true",nativeQuery = true)
    Page<Record> findAllDeletedRecord(Pageable pageable);

    // 查找所有未被软删除的投票对象
    @Query(value = "select * from vote_records where is_del = false",nativeQuery = true)
    Page<Record> findAllExistRecord(Pageable pageable);

    // 通过id找投票对象
    Record findRecordById(String id);
}
