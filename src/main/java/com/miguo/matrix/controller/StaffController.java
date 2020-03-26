package com.miguo.matrix.controller;

import com.miguo.matrix.dto.PageResult;
import com.miguo.matrix.dto.Result;
import com.miguo.matrix.dto.SearchDto;
import com.miguo.matrix.entity.client.Article;
import com.miguo.matrix.entity.client.Swiper;
import com.miguo.matrix.entity.client.Video;
import com.miguo.matrix.entity.staff.Account;
import com.miguo.matrix.service.client.ArticleService;
import com.miguo.matrix.service.client.SwiperService;
import com.miguo.matrix.service.client.VideoService;
import com.miguo.matrix.service.staff.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Hocassian
 */
@Api("员工管理接口，对视频、文章进行增删改")
@Slf4j
@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private SwiperService swiperService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private HttpSession session;

    //    -----------以下为员工信息的增删改查----------


    @ApiOperation("获取当前员工信息")
    @PostMapping("/account/find_one")
    public Result<Account> findOneStaff(String a){
        Result<Account> result = new Result<>();
        try {
            Account account = accountService.findOne((String) session.getAttribute("user"));
            result.setMessage("success").setCode(HttpStatus.OK).setData(account);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    @ApiOperation(value = "修改员工信息")
    @PostMapping("/account/update")
    public Result<String> updateStaffInfo(@RequestBody Account account) {
        Result<String> result = new Result<>();
        result.setMessage("update").setCode(HttpStatus.OK);
        try {
            accountService.update(account);
            result.setData("success");
        } catch (Exception e) {
            result.setData("fail");
        }

        return result;
    }

    //    -----------以下为文章的增删改查----------

    @ApiOperation("文章的增加")
    @PostMapping("/article/add")
    public Result<String> articleAdd(@RequestBody Article article){
        Result<String> result = new Result<>();
        result.setMessage("add").setCode(HttpStatus.OK);
        try{
            String id = articleService.add(article);
            result.setData(id).setCode(HttpStatus.OK).setMessage("success");
        }catch (Exception e){
            result.setData("fail");
        }
        return result;
    }

    @ApiOperation("批量下架文章")
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

    @ApiOperation("删除单篇文章")
    @DeleteMapping("/article/delete_one/{id}")
    public Result<String> articleDeleteOne(@PathVariable("id") String id){
        Result<String> result = new Result<>();
        result.setCode(HttpStatus.OK).setMessage("delete");
        try{
            articleService.deleteOne(id);
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

    @ApiOperation("分页查找所有包含关键字的文章")
    @PostMapping("/article/find_all_by_keywords")
    public Result<PageResult<Article>> articleFindAllByKeywords(@RequestBody SearchDto searchDto) {
        Result<PageResult<Article>> result = new Result<>();
        Page<Article> page;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                page = articleService.staffFindAllByKeywords("", searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
                // 当关键字为空时，查询所有
            } else {
                page = articleService.staffFindAllByKeywords(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<Article> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    @ApiOperation("获取某一篇文章")
    @GetMapping("/article/find_one_by_id/{id}")
    public Result<Article> findArticleById(@PathVariable("id") String id){
        Result<Article> result = new Result<>();
        try {
            Article list = articleService.findOneById(id);
            result.setMessage("success").setCode(HttpStatus.OK).setData(list);

        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    //    --------以下为视频的增删改查-------

    @ApiOperation("视频的添加")
    @PostMapping("/video/add")
    public Result<String> videoAdd(@RequestBody Video video){
        Result<String> result = new Result<>();
        try{
            videoService.add(video);
            result.setCode(HttpStatus.OK).setMessage("add").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("add").setData("fail");
        }
        return result;
    }

    @ApiOperation("视频的批量下架")
    @DeleteMapping("/video/delete/{ids}")
    public Result<String> videoDelete(@PathVariable("ids") String ids) {
        Result<String> result =new Result<>();
        try{
            videoService.delete(ids);
            result.setCode(HttpStatus.OK).setMessage("delete").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("delete").setData("fail");
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
    public Result<String> videoUpdate(@RequestBody Video video){
        Result<String> result = new Result<>();
        try{
            videoService.update(video);
            result.setCode(HttpStatus.OK).setMessage("update").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("update").setData("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有模糊查询的视频")
    @PostMapping("/video/find_all_by_keywords")
    public Result<PageResult<Video>> videoFindAllByKeywords(@RequestBody SearchDto searchDto) {
        Result<PageResult<Video>> result = new Result<>();
        Page<Video> page;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                page = videoService.staffFindAllByKeywords("", searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
                // 当关键字为空时，查询所有
            } else {
                page = videoService.staffFindAllByKeywords(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<Video> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }

        return result;
    }

    @ApiOperation("获取某一个视频")
    @GetMapping("/video/find_one_by_id/{id}")
    public Result<Video> findVideoById(@PathVariable("id") String id){
        Result<Video> result = new Result<>();
        try {
            Video list = videoService.findOneById(id);
            result.setMessage("success").setCode(HttpStatus.OK).setData(list);

        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    // ----------以下为网站轮播图的增删改-------

    @ApiOperation("网站轮播图的添加")
    @PostMapping("/web_swiper/add")
    public Result<String> webSwiperAdd(@RequestBody Swiper swiper){
        Result<String> result = new Result<>();
        try{
            swiperService.add(swiper);
            result.setCode(HttpStatus.OK).setMessage("add").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("add").setData("fail");
        }
        return result;
    }

    @ApiOperation("网站轮播图的批量下架")
    @DeleteMapping("/web_swiper/delete/{ids}")
    public Result<String> webSwiperDelete(@PathVariable("ids") String ids) {
        Result<String> result =new Result<>();
        try{
            swiperService.deleteSome(ids);
            result.setCode(HttpStatus.OK).setMessage("delete").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("delete").setData("fail");
        }
        return result;
    }

    @ApiOperation("网站轮播图的更新")
    @PutMapping("/web_swiper/update")
    public Result<String> webSwiperUpdate(@RequestBody Swiper swiper){
        Result<String> result = new Result<>();
        try{
            swiperService.update(swiper);
            result.setCode(HttpStatus.OK).setMessage("update").setData("success");
        }catch (Exception e){
            System.out.print(e);
            result.setCode(HttpStatus.OK).setMessage("update").setData("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有已被下架的网站轮播图")
    @GetMapping("/web_swiper/find_all_deleted/{page}/{size}")
    public Result<PageResult<Swiper>> webSwiperFindAllDeleted(@PathVariable("page") int page, @PathVariable("size") int size){
        Result<PageResult<Swiper>> result = new Result<>();
        Page<Swiper> pageTemp;
        try {
            pageTemp = swiperService.findAllDeleted(page, size);
            PageResult<Swiper> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有未被删除的网站轮播图")
    @GetMapping("/web_swiper/find_all_exist/{page}/{size}")
    public Result<PageResult<Swiper>> webSwiperFindAllExist(@PathVariable("page") int page, @PathVariable("size") int size){
        Result<PageResult<Swiper>> result = new Result<>();
        Page<Swiper> pageTemp;
        try {
            pageTemp = swiperService.findAllExist(page, size);
            PageResult<Swiper> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有模糊查询的视频")
    @PostMapping("/web_swiper/find_all_by_keywords")
    public Result<PageResult<Swiper>> webSwiperFindAllByKeywords(@RequestBody SearchDto searchDto) {
        Result<PageResult<Swiper>> result = new Result<>();
        Page<Swiper> page;
        try {
            if (searchDto.getKeywords() == null || searchDto.getKeywords().equals("")) {
                page = swiperService.staffFindAllByKeywords("", searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
                // 当关键字为空时，查询所有
            } else {
                page = swiperService.staffFindAllByKeywords(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<Swiper> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }

        return result;
    }

    @ApiOperation("获取某一个轮播图")
    @GetMapping("/web_swiper/find_one_by_id/{id}")
    public Result<Swiper> findSwiperById(@PathVariable("id") String id){
        Result<Swiper> result = new Result<>();
        try {
            Swiper list = swiperService.findOneById(id).get();
            result.setMessage("success").setCode(HttpStatus.OK).setData(list);

        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

}
