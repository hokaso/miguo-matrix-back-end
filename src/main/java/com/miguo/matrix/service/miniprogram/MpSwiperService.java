package com.miguo.matrix.service.miniprogram;

import com.miguo.matrix.entity.miniprogram.MpSwiper;
import com.miguo.matrix.repository.miniprogram.MpSwiperRepository;
import com.miguo.matrix.utils.SnowflakeIdWorker;
import com.miguo.matrix.vo.miniprogram.SwiperVo;
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

    public void add(MpSwiper mpSwiper){
        mpSwiper.setCreateAt(new Date());
        mpSwiper.setUpdateAt(new Date());
        mpSwiper.setId(snowflakeIdWorker.nextId());
        mpSwiper.setIsDel(false);
        mpSwiper.setCreateBy((String) session.getAttribute("user"));
        mpSwiper.setUpdateBy((String) session.getAttribute("user"));
        swiperRepository.save(mpSwiper);
    }

    public void delete(List<MpSwiper> list){
        swiperRepository.deleteInBatch(list);
    }

    public Page<SwiperVo> findSwiperByKeywords(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "updateAt");
        return swiperRepository.findSwiperByKeywords(keywords,pageable);
    }

    public MpSwiper findOneById(String id)
    {
        return swiperRepository.findSwiperById(id);
    }

    public void update(MpSwiper mpSwiper){
        MpSwiper mpSwiperTemp =this.findOneById(mpSwiper.getId());
        BeanUtils.copyProperties(mpSwiper, mpSwiperTemp);
        mpSwiperTemp.setUpdateBy((String) session.getAttribute("user"));
        mpSwiperTemp.setUpdateAt(new Date());
        swiperRepository.saveAndFlush(mpSwiperTemp);
    }

    public List<MpSwiper> findActiveOne(String id) {
        return swiperRepository.findActiveSwiper(id);
    }


}
