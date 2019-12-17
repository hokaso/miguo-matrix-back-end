package com.miguo.matrix.service.miniprogram;

import com.miguo.matrix.entity.miniprogram.Activity;
import com.miguo.matrix.repository.miniprogram.MpActivityRepository;
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
public class MpActivityService {

    @Autowired
    private MpActivityRepository activityRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    // 添加活动
    public void add(Activity activity){
        activity.setCreateAt(new Date());
        activity.setUpdateAt(new Date());
        activity.setId(snowflakeIdWorker.nextId());
        activity.setIsDel(false);
        activity.setCreateBy("test"); // 写死，到时候用session代替
        activity.setUpdateBy("test"); // 写死，到时候用session代替
        activityRepository.save(activity);
    }

    // 批量软删除活动
    public void delete(String ids){
        String deleteIds[] = ids.split(",");
        for(int i =0;i<deleteIds.length;i++){
            activityRepository.deleteById(deleteIds[i],new Date(),"test");  // 写死，用session代替
        }
    }

    // 分页查找所有已被删除的活动
    public Page<Activity> findAllDeleted(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        Page<Activity> pageTemp = activityRepository.findAllDeletedActivity(pageable);
        return pageTemp;
    }

    // 分页查找所有未被删除的活动
    public Page<Activity> findAllExist(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        Page<Activity> pageTemp = activityRepository.findAllExistActivity(pageable);
        return pageTemp;
    }

    // 不分页查找所有未被删除的活动

    // 通过id查找该活动
    public Activity findOneById(String id)
    {
        return activityRepository.findActivityById(id);
    }

    // 更新活动
    public void update(Activity activity){
        Activity activityTemp=this.findOneById(activity.getId());
        BeanUtils.copyProperties(activity, activityTemp);
        activityTemp.setUpdateBy("test"); // 写死，session代替
        activityTemp.setUpdateAt(new Date());
        activityRepository.saveAndFlush(activityTemp);
    }
    
}
