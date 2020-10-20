package com.miguo.matrix.repository.miniprogram;

import com.miguo.matrix.entity.miniprogram.Note;
import com.miguo.matrix.vo.miniprogram.NoteVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Hocassian
 */
@Repository
public interface MpNoteRepository extends JpaRepository<Note,String> {

    /**
     * 分页查找所有标题或者内容包含该关键字且未被软删除的活动（录入活动页面用,「keywords」为空时返回所有）
     * @param keywords
     * @param pageable
     * @return
     */
    @Query("SELECT new com.miguo.matrix.vo.miniprogram.NoteVo(" +
            "g.id," +
            "g.createBy," +
            "g.updateBy," +
            "g.createAt," +
            "g.updateAt," +
            "g.isDel," +
            "g.activityId," +
            "g.noteName," +
            "g.noteDisplayTime," +
            "g.noteDetails," +
            "a.activityName) " +
            "FROM Note g " +
            "LEFT JOIN Activity a ON g.activityId=a.id WHERE g.noteName LIKE %:#{#keywords}% OR g.noteDetails LIKE %:#{#keywords}%")
    Page<NoteVo> findNoteByKeywords(String keywords, Pageable pageable);

    /**
     * 通过id找公告对象
     * @param id
     * @return
     */
    Note findNoteById(String id);

    /**
     * 通过id找激活的公告对象
     * @param id
     * @param date
     * @return
     */
    @Query(value = "select * from vote_notes WHERE activity_id = :#{#id} and note_display_time < cast(:date as datetime) order by note_display_time DESC",nativeQuery = true)
    List<Note> findActiveNote(String id, Date date);
}
