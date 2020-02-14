package com.miguo.matrix.service.covid;

import com.miguo.matrix.entity.covid.StaffList;
import com.miguo.matrix.repository.covid.MpListRepository;
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
import java.util.Optional;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2019-12-17 10:02
 */
@Service
public class MpListService {

    @Autowired
    private MpListRepository listRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private HttpSession session;

    /**
     * 添加投票对象
     * @param staffList
     */
    public void add(StaffList staffList){
        StaffList healthTemp = new StaffList();
        BeanUtils.copyProperties(staffList, healthTemp);
        healthTemp.setCreateAt(new Date());
        healthTemp.setUpdateAt(new Date());
        healthTemp.setId(snowflakeIdWorker.nextId());
        healthTemp.setIsDel(false);
        listRepository.save(healthTemp);
    }

    public Page<StaffList> findListByKeywords(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "updateAt");
        return listRepository.findListByKeywords(keywords,pageable);
    }

    public Optional<StaffList> findById(String id)
    {
        return listRepository.findById(id);
    }

    public StaffList findOneById(String id)
    {
        return listRepository.findListById(id);
    }

    public void update(StaffList health){
        StaffList healthTemp=this.findOneById(health.getId());
        BeanUtils.copyProperties(health, healthTemp);
        healthTemp.setUpdateAt(new Date());
        listRepository.saveAndFlush(healthTemp);
    }

}
