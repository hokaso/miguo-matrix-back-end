package com.miguo.matrix.service.miniprogram;

import com.miguo.matrix.entity.miniprogram.Group;
import com.miguo.matrix.repository.miniprogram.MpGroupRepository;
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
public class MpGroupService {

    @Autowired
    private MpGroupRepository groupRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    // 添加投票对象
    public void add(Group group){
        group.setCreateAt(new Date());
        group.setUpdateAt(new Date());
        group.setId(snowflakeIdWorker.nextId());
        group.setIsDel(false);
        group.setCreateBy("test"); // 写死，到时候用session代替
        group.setUpdateBy("test"); // 写死，到时候用session代替
        groupRepository.save(group);
    }

    // 批量软删除投票对象
    public void delete(String ids){
        String deleteIds[] = ids.split(",");
        for(int i =0;i<deleteIds.length;i++){
            groupRepository.deleteById(deleteIds[i],new Date(),"test");  // 写死，用session代替
        }
    }

    // 查找所有已被删除的投票对象
    public Page<Group> findAllDeleted(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        Page<Group> pageTemp = groupRepository.findAllDeletedGroup(pageable);
        return pageTemp;
    }

    // 查找所有未被删除的投票对象
    public Page<Group> findAllExist(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        Page<Group> pageTemp = groupRepository.findAllExistGroup(pageable);
        return pageTemp;
    }

    // 通过id查找该投票对象
    public Group findOneById(String id)
    {
        return groupRepository.findGroupById(id);
    }

    // 更新投票对象
    public void update(Group group){
        Group groupTemp=this.findOneById(group.getId());
        BeanUtils.copyProperties(group, groupTemp);
        groupTemp.setUpdateBy("test"); // 写死，session代替
        groupTemp.setUpdateAt(new Date());
        groupRepository.saveAndFlush(groupTemp);
    }
    
}
