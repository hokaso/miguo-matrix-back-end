package com.miguo.matrix.service.covid;

import com.miguo.matrix.entity.covid.StaffHealth;
import com.miguo.matrix.repository.covid.MpHealthRepository;
import com.miguo.matrix.utils.SnowflakeIdWorker;
import com.miguo.matrix.vo.covid.HealthVo;
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
public class MpHealthService {

    @Autowired
    private MpHealthRepository healthRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private HttpSession session;

    /**
     * 添加投票对象
     * @param healthVo
     */
    public void add(HealthVo healthVo){
        StaffHealth healthTemp = new StaffHealth();
        BeanUtils.copyProperties(healthVo, healthTemp);
        healthTemp.setCreateAt(new Date());
        healthTemp.setUpdateAt(new Date());
        healthTemp.setId(snowflakeIdWorker.nextId());
        healthTemp.setIsDel(false);
        healthRepository.save(healthTemp);
    }

    public Page<HealthVo> findHealthByKeywords(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "updateAt");
        return healthRepository.findHealthByKeywords(keywords,pageable);
    }

    public Optional<StaffHealth> findById(String id)
    {
        return healthRepository.findById(id);
    }

    public StaffHealth findOneById(String id)
    {
        return healthRepository.findHealthById(id);
    }

    public void update(StaffHealth health){
        StaffHealth healthTemp=this.findOneById(health.getId());
        BeanUtils.copyProperties(health, healthTemp);
        healthTemp.setUpdateAt(new Date());
        healthRepository.saveAndFlush(healthTemp);
    }
    
}
