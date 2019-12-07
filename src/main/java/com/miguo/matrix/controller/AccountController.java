package com.miguo.matrix.controller;

import com.miguo.matrix.dto.PageResult;
import com.miguo.matrix.dto.Result;
import com.miguo.matrix.entity.client.Article;
import com.miguo.matrix.entity.client.Video;
import com.miguo.matrix.service.client.ArticleService;
import com.miguo.matrix.service.client.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api("员工管理接口，对视频、文章进行增删改")
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private VideoService videoService;

    @ApiOperation("文章的增加")
    @PostMapping("/article/add")
    public Result<String> articleAdd(@RequestBody Article article){
        Result<String> result = new Result<>();
        result.setMessage("add").setCode(HttpStatus.OK);
        try{
            articleService.add(article);
            result.setData("success");
        }catch (Exception e){
            result.setData("fail");
        }
        return result;
    }


    @ApiOperation("批量软删除文章")
    @DeleteMapping("/article/delete/{ids}")
    public Result<String> articleDelete(@PathVariable("ids") String ids){
        Result<String> result = new Result<>();
        result.setCode(HttpStatus.OK).setMessage("delete");
        try{
           articleService.delete(ids);
           result.setData("success");
        }catch (Exception e){
            result.setData("fail");
        }
        return result;
    }

    @ApiOperation("更新文章")
    @PutMapping("/article/update")
    public Result<String> articleUpdate(@RequestBody Article article) {
        Result<String> result = new Result<>();
        result.setCode(HttpStatus.OK).setMessage("update");
        try{
            articleService.update(article);
            result.setData("success");
        }catch (Exception e){
            result.setData("fail");
        }
        return result;
    }


    @ApiOperation("查找所有已被删除的文章")
    @GetMapping("/article/find_all_deleted/{page}/{size}")
    public Result<PageResult<Article>> articleFindAllDeleted(@PathVariable("page") int page, @PathVariable("size") int size) {
        Result<PageResult<Article>> result = new Result<>();
        Page<Article> page1;
        try {
            page1 = articleService.findAllDeleted(page, size);
            PageResult<Article> pageResult = new PageResult<>();
            pageResult.setTotal(page1.getTotalElements()).setData(page1.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("查找所有已被删除的视频")
    @GetMapping("/video/find_all_deleted/{page}/{size}")
    public Result<PageResult<Video>> videoFindAllDeleted(@PathVariable("page") int page, @PathVariable("size") int size){
        Result<PageResult<Video>> result = new Result<>();
        Page<Video> page1;
        try {
            page1 = videoService.findAllDeleted(page, size);
            PageResult<Video> pageResult = new PageResult<>();
            pageResult.setTotal(page1.getTotalElements()).setData(page1.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

}
