package com.miguo.matrix.service.miniprogram;

import com.miguo.matrix.entity.miniprogram.Swiper;
import com.miguo.matrix.repository.miniprogram.MpSwiperRepository;
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
public class MpSwiperService {

    @Autowired
    private MpSwiperRepository swiperRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private HttpSession session;

    public void add(Swiper swiper){
        swiper.setCreateAt(new Date());
        swiper.setUpdateAt(new Date());
        swiper.setId(snowflakeIdWorker.nextId());
        swiper.setIsDel(false);
        swiper.setCreateBy((String) session.getAttribute("user"));
        swiper.setUpdateBy((String) session.getAttribute("user"));
        swiperRepository.save(swiper);
    }

    public void delete(List<Swiper> list){
        swiperRepository.deleteInBatch(list);
    }

    public Page<Swiper> findSwiperByKeywords(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "update_at");
        return swiperRepository.findSwiperByKeywords(keywords,pageable);
    }

    public Swiper findOneById(String id)
    {
        return swiperRepository.findSwiperById(id);
    }

    public void update(Swiper swiper){
        Swiper swiperTemp=this.findOneById(swiper.getId());
        BeanUtils.copyProperties(swiper, swiperTemp);
        swiperTemp.setUpdateBy((String) session.getAttribute("user"));
        swiperTemp.setUpdateAt(new Date());
        swiperRepository.saveAndFlush(swiperTemp);
    }
    
}
