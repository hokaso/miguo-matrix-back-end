package com.miguo.matrix.service.miniprogram;

import com.miguo.matrix.entity.miniprogram.Record;
import com.miguo.matrix.repository.miniprogram.MpRecordRepository;
import com.miguo.matrix.utils.SnowflakeIdWorker;
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
public class MpRecordService {

    @Autowired
    private MpRecordRepository recordRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private HttpSession session;

    public void add(Record record){
        record.setCreateAt(new Date());
        record.setUpdateAt(new Date());
        record.setId(snowflakeIdWorker.nextId());
        record.setIsDel(false);
        record.setCreateBy((String) session.getAttribute("user"));
        record.setUpdateBy((String) session.getAttribute("user"));
        recordRepository.save(record);
    }

    public void delete(List<Record> list){
        recordRepository.deleteInBatch(list);
    }

    public Page<Record> findRecordByKeywords(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "update_at");
        return recordRepository.findRecordByKeywords(keywords,pageable);
    }

    public Record findOneById(String id)
    {
        return recordRepository.findRecordById(id);
    }

    public void update(Record record){
        Record recordTemp=this.findOneById(record.getId());
        BeanUtils.copyProperties(record, recordTemp);
        recordTemp.setUpdateBy((String) session.getAttribute("user"));
        recordTemp.setUpdateAt(new Date());
        recordRepository.saveAndFlush(recordTemp);
    }
}
