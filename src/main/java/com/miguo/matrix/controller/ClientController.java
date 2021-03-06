package com.miguo.matrix.controller;

import com.miguo.matrix.dto.SearchDto;
import com.miguo.matrix.dto.PageResult;
import com.miguo.matrix.dto.Result;
import com.miguo.matrix.entity.client.Article;
import com.miguo.matrix.entity.client.Swiper;
import com.miguo.matrix.entity.client.Video;
import com.miguo.matrix.service.client.ArticleService;
import com.miguo.matrix.service.client.SwiperService;
import com.miguo.matrix.service.client.VideoService;
import com.miguo.matrix.vo.web.ArticleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Hocassian
 */
@Api("用户专用接口，无需登陆，对视频、文章的查看")
@Slf4j
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private SwiperService swiperService;

    @ApiOperation("查找挂上首页的文章")
    @PostMapping("/article/find_some")
    public Result<PageResult<ArticleVo>> articleFindSome(@RequestBody SearchDto searchDto) {
        Result<PageResult<ArticleVo>> result = new Result<>();
        Page<ArticleVo> page;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                page = articleService.findSome("", searchDto.getPage(), searchDto.getSize());
                // 当关键字为空时，查询所有
            } else {
                page = articleService.findSome(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize());
            }
            PageResult<ArticleVo> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }

        return result;
    }

    @ApiOperation("分页查找所有模糊查询且未被软删除的文章")
    @PostMapping("/article/find_all_by_keywords")
    public Result<PageResult<Article>> articleFindAllByKeywords(@RequestBody SearchDto searchDto) {
        Result<PageResult<Article>> result = new Result<>();
        Page<Article> page;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                page = articleService.findAllByKeywords("", searchDto.getPage(), searchDto.getSize());
                // 当关键字为空时，查询所有
            } else {
                page = articleService.findAllByKeywords(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize());
            }
            PageResult<Article> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }

        return result;
    }

    @ApiOperation("找某一篇文章")
    @GetMapping("/article/find_one_by_id/{id}")
    public Result<Article> articleFindOneById(@PathVariable("id") String id) {
        Result<Article> result = new Result<>();
        result.setMessage("find_one_by_id").setCode(HttpStatus.OK);
        try {
            Article article = articleService.findOneById(id);
            result.setData(article).setMessage("success").setCode(HttpStatus.OK);
        } catch (Exception e) {
            result.setData(null).setMessage("fail").setCode(HttpStatus.OK);
        }
        return result;
    }

    @ApiOperation("分页查找所有模糊查询且未被软删除的视频")
    @PostMapping("/video/find_all_by_keywords")
    public Result<PageResult<Video>> videoFindAllByKeywords(@RequestBody SearchDto searchDto) {
        Result<PageResult<Video>> result = new Result<>();
        Page<Video> page;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                page = videoService.findAllByKeywords("", searchDto.getPage(), searchDto.getSize());
            // 当关键字为空时，查询所有
            } else {
                page = videoService.findAllByKeywords(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize());
            }
            PageResult<Video> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    @ApiOperation("找某一支视频")
    @GetMapping("/video/find_one_by_id/{id}")
    public Result<Video> videoFindOneById(@PathVariable("id") String id) {
        Result<Video> result = new Result<>();
        result.setMessage("find_one_by_id").setCode(HttpStatus.OK);
        try {
            Video video = videoService.findOneById(id);
            result.setData(video).setMessage("success").setCode(HttpStatus.OK);
        } catch (Exception e) {
            result.setData(null).setMessage("fail").setCode(HttpStatus.OK);
        }
        return result;
    }

    @ApiOperation("找某一个轮播图")
    @GetMapping("/swiper/find_one_by_id/{id}")
    public Result<Swiper> swiperFindOneById(@PathVariable("id") String id) {
        Result<Swiper> result = new Result<>();
        result.setMessage("find_one_by_id").setCode(HttpStatus.OK);
        try {
            Swiper swiper = swiperService.findOneById(id).orElse(null);
            result.setData(swiper).setMessage("success").setCode(HttpStatus.OK);
        } catch (Exception e) {
            result.setData(null).setMessage("fail").setCode(HttpStatus.OK);
        }
        return result;
    }

    @ApiOperation("查找所有未被软删除的轮播图")
    @PostMapping("/swiper/find_all_exist")
    public Result<List<Swiper>> swiperFindAllByKeywords(String a) {
        Result<List<Swiper>> result = new Result<>();
        List<Swiper> list;
        try {
            list = swiperService.clientFindAllExist();
            result.setMessage("success").setCode(HttpStatus.OK).setData(list);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }
}
