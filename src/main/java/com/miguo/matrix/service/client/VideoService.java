package com.miguo.matrix.service.client;

import com.miguo.matrix.entity.client.Video;
import com.miguo.matrix.repository.client.VideoRepository;
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

/**
 * @author Hocassian
 */
@Service
public class VideoService {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private HttpSession session;

    /**
     * 批量下架视频
     * @param ids
     */
    public void delete(String ids){
        String[] deleteIds = ids.split(",");
        for (String deleteId : deleteIds) {
            videoRepository.deleteSomeById(deleteId, new Date(), (String) session.getAttribute("user"));
        }

    }

    /**
     * 添加视频
     * @param video
     */
    public void add(Video video){
       video.setVideoDate(new Date());
       video.setCreateAt(new Date());
       video.setUpdateAt(new Date());
       video.setUpdateBy((String) session.getAttribute("user"));
       video.setCreateBy((String) session.getAttribute("user"));
       video.setId(snowflakeIdWorker.nextId());
       video.setIsDel(false);
       videoRepository.save(video);
    }

    /**
     * 分页返回标题或内容包含某关键字且未被下架的视频条目（客户使用）
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    public Page<Video> findAllByKeywords(String keywords,int page,int size){
        page--;
        Pageable pageable = PageRequest.of(page, size);
        return videoRepository.findVideoByKeywords(keywords,pageable);
    }

    /**
     * 分页返回标题或内容包含某关键字的视频条目（员工使用），当关键字为空时按更新时间顺序返回所有
     * @param keywords
     * @param page
     * @param size
     * @param direction
     * @return
     */
    public Page<Video> staffFindAllByKeywords(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "update_at");
        return videoRepository.staffFindVideoByKeywords(keywords,pageable);
    }

    /**
     * 分页返回所有已被下架的视频条目
     * @param page
     * @param size
     * @return
     */
    public Page<Video> findAllDeleted(int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        return videoRepository.findAllDeletedVideo(pageable);
    }

    /**
     * 分页返回所有未被下架的视频条目
     * @param page
     * @param size
     * @return
     */
    public Page<Video> findAllExist(int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        return videoRepository.findAllExistVideo(pageable);
    }

    /**
     * 通过id找视频
     * @param id
     * @return
     */
    public Video findOneById(String id){
        return videoRepository.findVideoById(id);
    }

    /**
     * 更新视频
     * @param video
     */
    public void update(Video video){
        Video videoTemp=this.findOneById(video.getId());
        BeanUtils.copyProperties(video, videoTemp);
        videoTemp.setUpdateAt(new Date());
        videoTemp.setUpdateBy((String) session.getAttribute("user"));
        videoRepository.saveAndFlush(videoTemp);
    }

    /**
     * 删除一个视频
     * @param id
     */
    public void deleteOne(String id){
        videoRepository.deleteById(id);
    }
}
