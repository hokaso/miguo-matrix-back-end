package com.miguo.matrix.service.client;

import com.miguo.matrix.entity.client.Swiper;
import com.miguo.matrix.repository.client.SwiperRepository;
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
public class SwiperService {

    @Autowired
    private SwiperRepository swiperRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    // 添加轮播图
    public void add(Swiper swiper){
        swiper.setCreateAt(new Date());
        swiper.setUpdateAt(new Date());
        swiper.setId(snowflakeIdWorker.nextId());
        swiper.setIsDel(false);
        swiper.setCreateBy("test"); // 写死，到时候用session代替
        swiper.setUpdateBy("test"); // 写死，到时候用session代替
        swiperRepository.save(swiper);
    }

    // 批量软删除轮播图
    public void delete(String ids){
        String deleteIds[] = ids.split(",");
        for(int i =0;i<deleteIds.length;i++){
            swiperRepository.deleteById(deleteIds[i],new Date(),"test");  // 写死，用session代替
        }
    }

    // 查找所有已被删除的轮播图
    public Page<Swiper> findAllDeleted(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        Page<Swiper> page1 = swiperRepository.findAllDeletedSwiper(pageable);
        return page1;
    }

    // 查找所有未被删除的轮播图
    public Page<Swiper> findAllExist(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        Page<Swiper> page1 = swiperRepository.findAllExistSwiper(pageable);
        return page1;
    }

    // 通过id查找该轮播图
    public Swiper findOneById(String id)
    {
        return swiperRepository.findSwiperById(id);
    }

    // 更新轮播图
    public void update(Swiper swiper){
        Swiper swiperTemp=this.findOneById(swiper.getId());
        BeanUtils.copyProperties(swiper, swiperTemp);
        swiperTemp.setUpdateBy("test"); // 写死，session代替
        swiperTemp.setUpdateAt(new Date());
        swiperRepository.saveAndFlush(swiperTemp);
    }
    
}
