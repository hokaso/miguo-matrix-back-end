package com.miguo.matrix.service.media;

import com.miguo.matrix.entity.media.MediaVideo;
import com.miguo.matrix.repository.media.MediaVideoRepository;
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
public class MediaVideoService {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private MediaVideoRepository videoRepository;

    @Autowired
    private HttpSession session;


    /**
     * 添加视频
     * @param mediaVideo
     */
    public void add(MediaVideo mediaVideo){
       mediaVideo.setCreateAt(new Date());
       mediaVideo.setUpdateAt(new Date());
       mediaVideo.setUpdateBy((String) session.getAttribute("user"));
       mediaVideo.setCreateBy((String) session.getAttribute("user"));
       mediaVideo.setId(snowflakeIdWorker.nextId());
       mediaVideo.setIsDel(false);
       videoRepository.save(mediaVideo);
    }


    /**
     * 分页返回标题或内容包含某关键字的视频条目（员工使用），当关键字为空时按更新时间顺序返回所有
     * @param keywords
     * @param page
     * @param size
     * @param direction
     * @return
     */
    public Page<MediaVideo> staffFindAllByKeywords(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "update_at");
        return videoRepository.staffFindVideoByKeywords(keywords,pageable);
    }

    /**
     * 分页分类返回所有视频条目
     * @param page
     * @param size
     * @return
     */
    public Page<MediaVideo> findAllClass(String keywords, int page, int size, String active, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page,size, direction, "create_at");
        Boolean activeTemp = Boolean.parseBoolean(active);
        return videoRepository.findAllClassVideo(keywords, pageable, activeTemp);
    }

    /**
     * 分页不分类返回所有视频条目
     * @param page
     * @param size
     * @return
     */
    public Page<MediaVideo> findAllExist(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "create_at");
        return videoRepository.findAllExistVideo(keywords, pageable);
    }

    /**
     * 通过id找视频
     * @param id
     * @return
     */
    public MediaVideo findOneById(String id){
        return videoRepository.findVideoById(id);
    }

    /**
     * 更新视频
     * @param mediaVideo
     */
    public void update(MediaVideo mediaVideo){
        MediaVideo mediaVideoTemp =this.findOneById(mediaVideo.getId());
        BeanUtils.copyProperties(mediaVideo, mediaVideoTemp);
        mediaVideoTemp.setUpdateAt(new Date());
        mediaVideoTemp.setUpdateBy((String) session.getAttribute("user"));
        videoRepository.saveAndFlush(mediaVideoTemp);
    }

    /**
     * 删除一个视频
     * @param id
     */
    public void deleteOne(String id){
        videoRepository.deleteById(id);
    }
}
