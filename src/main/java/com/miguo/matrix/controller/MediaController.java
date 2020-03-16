package com.miguo.matrix.controller;

import com.miguo.matrix.dto.PageResult;
import com.miguo.matrix.dto.Result;
import com.miguo.matrix.dto.SearchDto;
import com.miguo.matrix.entity.media.MediaVideo;
import com.miguo.matrix.service.media.MediaVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2020-03-13 10:24
 */

@Api("员工管理接口，对视频、文章进行全平台分发")
@Slf4j
@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaVideoService videoService;

    @Autowired
    private HttpSession session;

    @ApiOperation("视频的添加")
    @PostMapping("/video/add")
    public Result<String> videoAdd(@RequestBody MediaVideo mediaVideo){
        Result<String> result = new Result<>();
        try{
            videoService.add(mediaVideo);
            result.setCode(HttpStatus.OK).setMessage("add").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("add").setData("fail");
        }
        return result;
    }

    @ApiOperation("删除单个视频")
    @DeleteMapping("/video/delete_one/{id}")
    public Result<String> videoDeleteOne(@PathVariable("id") String id){
        Result<String> result = new Result<>();
        result.setCode(HttpStatus.OK).setMessage("delete");
        try{
            videoService.deleteOne(id);
            result.setData("success");
        }catch (Exception e){
            result.setData("fail");
        }
        return result;
    }

    @ApiOperation("视频的更新")
    @PutMapping("/video/update")
    public Result<String> videoUpdate(@RequestBody MediaVideo mediaVideo){
        Result<String> result = new Result<>();
        try{
            videoService.update(mediaVideo);
            result.setCode(HttpStatus.OK).setMessage("update").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("update").setData("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有模糊查询的视频")
    @PostMapping("/video/find_all_by_keywords")
    public Result<PageResult<MediaVideo>> videoFindAllByKeywords(@RequestBody SearchDto searchDto) {
        Result<PageResult<MediaVideo>> result = new Result<>();
        Page<MediaVideo> page;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                page = videoService.staffFindAllByKeywords("", searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
                // 当关键字为空时，查询所有
            } else {
                page = videoService.staffFindAllByKeywords(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<MediaVideo> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }

        return result;
    }

    @ApiOperation("获取某一个视频")
    @GetMapping("/video/find_one_by_id/{id}")
    public Result<MediaVideo> findVideoById(@PathVariable("id") String id){
        Result<MediaVideo> result = new Result<>();
        try {
            MediaVideo list = videoService.findOneById(id);
            result.setMessage("success").setCode(HttpStatus.OK).setData(list);

        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }


}
