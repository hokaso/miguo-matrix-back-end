package com.miguo.matrix.service.miniprogram;

import com.miguo.matrix.entity.miniprogram.Note;
import com.miguo.matrix.repository.miniprogram.MpNoteRepository;
import com.miguo.matrix.utils.SnowflakeIdWorker;
import com.miguo.matrix.vo.miniprogram.NoteVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2019-12-17 10:02
 */
@Service
public class MpNoteService {

    @Autowired
    private MpNoteRepository noteRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private HttpSession session;
    
    public void add(Note note){
        note.setCreateAt(new Date());
        note.setUpdateAt(new Date());
        note.setId(snowflakeIdWorker.nextId());
        note.setIsDel(false);
        note.setCreateBy((String) session.getAttribute("user"));
        note.setUpdateBy((String) session.getAttribute("user"));
        noteRepository.save(note);
    }

    public void delete(List<Note> list){
        noteRepository.deleteInBatch(list);
    }

    public Page<NoteVo> findNoteByKeywords(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "updateAt");
        return noteRepository.findNoteByKeywords(keywords,pageable);
    }

    public Note findOneById(String id)
    {
        return noteRepository.findNoteById(id);
    }

    public void update(Note note){
        Note noteTemp=this.findOneById(note.getId());
        BeanUtils.copyProperties(note, noteTemp);
        noteTemp.setUpdateBy((String) session.getAttribute("user"));
        noteTemp.setUpdateAt(new Date());
        noteRepository.saveAndFlush(noteTemp);
    }

    public List<Note> findActiveOne(String id) {
        return noteRepository.findActiveNote(id,new Date());
    }
    
}
