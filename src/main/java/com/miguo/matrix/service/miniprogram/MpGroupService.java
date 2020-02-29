package com.miguo.matrix.service.miniprogram;

import com.miguo.matrix.entity.miniprogram.Group;
import com.miguo.matrix.repository.miniprogram.MpGroupRepository;
import com.miguo.matrix.utils.SnowflakeIdWorker;
import com.miguo.matrix.vo.miniprogram.GroupVo;
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
import java.util.Optional;

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
     * @param groupVo
     */
    public void add(GroupVo groupVo){
        Group groupTemp = new Group();
        BeanUtils.copyProperties(groupVo, groupTemp);
        groupTemp.setCreateAt(new Date());
        groupTemp.setUpdateAt(new Date());
        groupTemp.setId(snowflakeIdWorker.nextId());
        groupTemp.setIsDel(false);
        groupTemp.setCreateBy((String) session.getAttribute("user"));
        groupTemp.setUpdateBy((String) session.getAttribute("user"));
        groupTemp.setGroupVotes(0);
        groupRepository.save(groupTemp);
    }

    /**
     * 删除&批量删除
     * @param list
     */
    public void delete(List<Group> list){
        groupRepository.deleteInBatch(list);
    }

    public Page<GroupVo> findGroupByKeywords(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "updateAt");
        return groupRepository.findGroupByKeywords(keywords,pageable);
    }

    public List<Group> findAllActivityByKeywordsFromInput (String keywords) {
        return groupRepository.findGroupByKeywordsFromInput(keywords);
    }

    public Optional<Group> findById(String id)
    {
        return groupRepository.findById(id);
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

    public List<Group> findActiveOne(String id) {
        return groupRepository.findActiveGroup(id);
    }

    public void vote(String id){
        groupRepository.voteForGroup(id);
    }
}
