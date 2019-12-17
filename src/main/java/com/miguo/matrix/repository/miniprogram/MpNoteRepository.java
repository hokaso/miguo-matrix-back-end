package com.miguo.matrix.repository.miniprogram;

import com.miguo.matrix.entity.miniprogram.Note;
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
public interface MpNoteRepository extends JpaRepository<Note,String> {

    // 软删除某一个投票对象
    @Modifying
    @Transactional
    @Query(value = "update vote_notes set is_del = true , update_at = :#{#date},update_by = :#{#updateBy} where id = :#{#id} ", nativeQuery = true)
    void deleteById(String id, Date date, String updateBy);

    // 查找所有已被软删除的投票对象
    @Query(value = "select * from vote_notes where is_del = true",nativeQuery = true)
    Page<Note> findAllDeletedNote(Pageable pageable);

    // 查找所有未被软删除的投票对象
    @Query(value = "select * from vote_notes where is_del = false",nativeQuery = true)
    Page<Note> findAllExistNote(Pageable pageable);

    // 通过id找投票对象
    Note findNoteById(String id);
}
