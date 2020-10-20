package com.miguo.matrix.service.client;

import com.miguo.matrix.entity.client.Swiper;
import com.miguo.matrix.repository.client.SwiperRepository;
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
public class SwiperService {

    @Autowired
    private SwiperRepository swiperRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private HttpSession session;

    /**
     * 添加文章
     * @param swiper
     */
    public void add(Swiper swiper){
        swiper.setCreateAt(new Date());
        swiper.setUpdateAt(new Date());
        swiper.setId(snowflakeIdWorker.nextId());
        swiper.setIsDel(false);
        swiper.setCreateBy((String) session.getAttribute("user"));
        swiper.setUpdateBy((String) session.getAttribute("user"));
        swiperRepository.save(swiper);
    }

    /**
     * 批量下架轮播图
     * @param ids
     */
    public void deleteSome(String ids){
        String[] deleteIds = ids.split(",");
        for (String deleteId : deleteIds) {
            swiperRepository.deleteById(deleteId, new Date(), (String) session.getAttribute("user"));
        }
    }

    /**
     * 删除一个轮播图
     * @param id
     */
    public void deleteOne(String id){
        swiperRepository.deleteById(id);
    }

    /**
     * 分页返回标题或内容包含某关键字的文章条目（员工使用），当关键字为空时按更新时间顺序返回所有
     * @param keywords
     * @param page
     * @param size
     * @param direction
     * @return
     */
    public Page<Swiper> staffFindAllByKeywords(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "update_at");
        return swiperRepository.staffFindSwiperByKeywords(keywords,pageable);
    }

    /**
     * 分页返回所有已被下架的轮播图条目
     * @param page
     * @param size
     * @return
     */
    public Page<Swiper> findAllDeleted(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        return swiperRepository.findAllDeletedSwiper(pageable);
    }

    /**
     * 分页返回所有未被下架的轮播图条目
     * @param page
     * @param size
     * @return
     */
    public Page<Swiper> findAllExist(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        return swiperRepository.findAllExistSwiper(pageable);
    }

    /**
     * 分页返回所有未被下架的轮播图条目
     * @param
     * @return
     */
    public List<Swiper> clientFindAllExist(){
        return swiperRepository.clientFindAllExistSwiper();
    }

    /**
     * 通过id找轮播图
     * @param id
     * @return
     */
    public Optional<Swiper> findOneById(String id)
    {
        return swiperRepository.findById(id);
    }

    /**
     * 更新轮播图
     * @param swiper
     */
    public void update(Swiper swiper){
        Swiper swiperTemp=this.findOneById(swiper.getId()).get();
        BeanUtils.copyProperties(swiper, swiperTemp);
        swiperTemp.setUpdateBy((String) session.getAttribute("user"));
        swiperTemp.setUpdateAt(new Date());
        swiperRepository.saveAndFlush(swiperTemp);
    }

    /**
     * ※审核方法
     *
     * 分页分类返回所有条目
     * @param page
     * @param size
     * @return
     */
    public Page<Swiper> findAllClass(String keywords, int page,int size, String active, Sort.Direction direction){
        page--;
        Pageable pageable=PageRequest.of(page,size, direction, "create_at");
        Boolean activeTemp = Boolean.parseBoolean(active);
        return swiperRepository.findAllClassSwiper(keywords, pageable, activeTemp);
    }

    /**
     * ※审核方法
     *
     * 分页不分类返回所有条目
     * @param page
     * @param size
     * @return
     */
    public Page<Swiper> findAllExist(String keywords, int page,int size, Sort.Direction direction){
        page--;
        Pageable pageable=PageRequest.of(page,size, direction, "create_at");
        return swiperRepository.findAllExistSwiper(keywords, pageable);
    }
}
