package com.miguo.matrix.service.miniprogram;

import com.miguo.matrix.entity.miniprogram.Group;
import com.miguo.matrix.repository.miniprogram.MpGroupRepository;
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
public class MpGroupService {

    @Autowired
    private MpGroupRepository groupRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private HttpSession session;

    /**
     * 添加投票对象
     * @param group
     */
    public void add(Group group){
        group.setCreateAt(new Date());
        group.setUpdateAt(new Date());
        group.setId(snowflakeIdWorker.nextId());
        group.setIsDel(false);
        group.setCreateBy((String) session.getAttribute("user"));
        group.setUpdateBy((String) session.getAttribute("user"));
        groupRepository.save(group);
    }

    /**
     * 删除&批量删除
     * @param list
     */
    public void delete(List<Group> list){
        groupRepository.deleteInBatch(list);
    }

    public Page<Group> findGroupByKeywords(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "update_at");
        return groupRepository.findGroupByKeywords(keywords,pageable);
    }

    public Group findOneById(String id)
    {
        return groupRepository.findGroupById(id);
    }

    public void update(Group group){
        Group groupTemp=this.findOneById(group.getId());
        BeanUtils.copyProperties(group, groupTemp);
        groupTemp.setUpdateBy((String) session.getAttribute("user"));
        groupTemp.setUpdateAt(new Date());
        groupRepository.saveAndFlush(groupTemp);
    }
    
}
