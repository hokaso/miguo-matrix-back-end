package com.miguo.matrix.service.miniprogram;

import com.miguo.matrix.entity.miniprogram.Activity;
import com.miguo.matrix.repository.miniprogram.MpActivityRepository;
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
import java.util.Optional;

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

    @Autowired
    private HttpSession session;

    /**
     * 添加活动
     * @param activity
     */
    public void add(Activity activity){
        activity.setCreateAt(new Date());
        activity.setUpdateAt(new Date());
        activity.setId(snowflakeIdWorker.nextId());
        activity.setIsDel(false);
        activity.setCreateBy((String) session.getAttribute("user"));
        activity.setUpdateBy((String) session.getAttribute("user"));
        activityRepository.save(activity);
    }

    /**
     * 删除&批量删除
     * @param list
     */
    public void delete(List<Activity> list){
        activityRepository.deleteInBatch(list);
    }

    /**
     * 分页查找所有标题或者内容包含该关键字且未被软删除的活动（录入活动页面用,「keywords」为空时返回所有）
     * @param page
     * @param size
     * @return
     */
    public Page<Activity> findAllActivityByKeywords(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "update_at");
        return activityRepository.findActivityByKeywords(keywords,pageable);
    }

    /**
     * 查找所有标题或者内容包含该关键字的活动（不分页，输入活动相关信息时联结用）
     * @param keywords
     * @return
     */
    public List<Activity> findAllActivityByKeywordsFromInput (String keywords) {
        return activityRepository.findActivityByKeywordsFromInput(keywords);
    }

    /**
     * 通过id查找活动（用于查找）
     * @param id
     * @return
     */
    public Optional<Activity> findById(String id)
    {
        return activityRepository.findById(id);
    }

    /**
     * 通过id查找活动（用于更新）
     * @param id
     * @return
     */
    public Activity findOneById(String id)
    {
        return activityRepository.findActivityById(id);
    }

    /**
     * 更新活动
     * @param activity
     */
    public void update(Activity activity){
        Activity activityTemp=this.findOneById(activity.getId());
        BeanUtils.copyProperties(activity, activityTemp);
        activityTemp.setUpdateBy((String) session.getAttribute("user"));
        activityTemp.setUpdateAt(new Date());
        activityRepository.saveAndFlush(activityTemp);
    }

    public List<Activity> findActiveOne() { return activityRepository.findActiveActivity(); }
    
}
