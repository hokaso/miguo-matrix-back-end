package com.miguo.matrix.service.client;

import com.miguo.matrix.entity.client.Article;
import com.miguo.matrix.entity.client.Video;
import com.miguo.matrix.repository.client.VideoRepository;
import com.miguo.matrix.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VideoService {
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    @Autowired
    private VideoRepository videoRepository;

    // 批量软删除视频
    public void delete(String ids){
        String deleteIds[] = ids.split(",");
        for(int i =0;i<deleteIds.length;i++){
            videoRepository.deleteById(deleteIds[i],new Date(),"test");  // 写死，用session代替
        }

    }

    // 添加视频
    public void add(Video video){
       video.setVideoDate(new Date());
       video.setCreateAt(new Date());
       video.setUpdateAt(new Date());
       video.setUpdateBy("test"); // 写死，用session代替
       video.setCreateBy("test"); // 写死，用session代替
       video.setId(snowflakeIdWorker.nextId());
       video.setIsDel(false);
       videoRepository.save(video);
    }

    // 查询 标题或者视频内容 包含 该关键字的所有视频
    public Page<Video> findAllByKeywords(String keywords,int page,int size){
        page--;
        Pageable pageable = PageRequest.of(page, size);
        Page<Video> page1 = videoRepository.finVideoByKeywords(keywords,pageable);
        return page1;
    }

    // 查找所有已被删除的视频
    public Page<Video> findAllDeleted(int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Video> page1 = videoRepository.findAllDeletedVideo(pageable);
        return page1;
    }

    // 通过id找视频
    public Video findOneById(String id){

        return videoRepository.findVideoById(id);
    }

    // 更新视频，注意video_date(创作日期)不能被更新
    public void update(Video video){
        Video video1=this.findOneById(video.getId());
        video1.setVideoUrl(video.getVideoUrl());
        video1.setVideoProfile(video.getVideoProfile());
        video1.setVideoPic(video.getVideoPic());
        video1.setVideoTitle(video.getVideoTitle());
        video1.setUpdateAt(new Date());
        video1.setUpdateBy("test"); // 写死，用session代替
        videoRepository.saveAndFlush(video1);
    }

}
