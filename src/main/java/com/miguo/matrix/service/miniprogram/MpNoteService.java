package com.miguo.matrix.service.miniprogram;

import com.miguo.matrix.entity.miniprogram.Note;
import com.miguo.matrix.repository.miniprogram.MpNoteRepository;
import com.miguo.matrix.utils.SnowflakeIdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    // 添加公告栏对象
    public void add(Note note){
        note.setCreateAt(new Date());
        note.setUpdateAt(new Date());
        note.setId(snowflakeIdWorker.nextId());
        note.setIsDel(false);
        note.setCreateBy("test"); // 写死，到时候用session代替
        note.setUpdateBy("test"); // 写死，到时候用session代替
        noteRepository.save(note);
    }

    // 批量软删除公告栏对象
    public void delete(String ids){
        String deleteIds[] = ids.split(",");
        for(int i =0;i<deleteIds.length;i++){
            noteRepository.deleteById(deleteIds[i],new Date(),"test");  // 写死，用session代替
        }
    }

    // 查找所有已被删除的公告栏对象
    public Page<Note> findAllDeleted(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        Page<Note> pageTemp = noteRepository.findAllDeletedNote(pageable);
        return pageTemp;
    }

    // 查找所有未被删除的公告栏对象
    public Page<Note> findAllExist(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        Page<Note> pageTemp = noteRepository.findAllExistNote(pageable);
        return pageTemp;
    }

    // 通过id查找该公告栏对象
    public Note findOneById(String id)
    {
        return noteRepository.findNoteById(id);
    }

    // 更新公告栏对象
    public void update(Note note){
        Note noteTemp=this.findOneById(note.getId());
        BeanUtils.copyProperties(note, noteTemp);
        noteTemp.setUpdateBy("test"); // 写死，session代替
        noteTemp.setUpdateAt(new Date());
        noteRepository.saveAndFlush(noteTemp);
    }
    
}
