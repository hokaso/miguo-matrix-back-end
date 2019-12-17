package com.miguo.matrix.service.miniprogram;

import com.miguo.matrix.entity.miniprogram.Record;
import com.miguo.matrix.repository.miniprogram.MpRecordRepository;
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
public class MpRecordService {

    @Autowired
    private MpRecordRepository recordRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    // 添加投票记录
    public void add(Record record){
        record.setCreateAt(new Date());
        record.setUpdateAt(new Date());
        record.setId(snowflakeIdWorker.nextId());
        record.setIsDel(false);
        record.setCreateBy("test"); // 写死，到时候用session代替
        record.setUpdateBy("test"); // 写死，到时候用session代替
        recordRepository.save(record);
    }

    // 批量软删除投票记录
    public void delete(String ids){
        String deleteIds[] = ids.split(",");
        for(int i =0;i<deleteIds.length;i++){
            recordRepository.deleteById(deleteIds[i],new Date(),"test");  // 写死，用session代替
        }
    }

    // 查找所有已被删除的投票记录
    public Page<Record> findAllDeleted(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        Page<Record> pageTemp = recordRepository.findAllDeletedRecord(pageable);
        return pageTemp;
    }

    // 查找所有未被删除的投票记录
    public Page<Record> findAllExist(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        Page<Record> pageTemp = recordRepository.findAllExistRecord(pageable);
        return pageTemp;
    }

    // 通过id查找该投票记录
    public Record findOneById(String id)
    {
        return recordRepository.findRecordById(id);
    }

    // 更新投票记录
    public void update(Record record){
        Record recordTemp=this.findOneById(record.getId());
        BeanUtils.copyProperties(record, recordTemp);
        recordTemp.setUpdateBy("test"); // 写死，session代替
        recordTemp.setUpdateAt(new Date());
        recordRepository.saveAndFlush(recordTemp);
    }
    
}
