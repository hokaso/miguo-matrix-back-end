package com.miguo.matrix.controller;

import com.miguo.matrix.dto.PageResult;
import com.miguo.matrix.dto.Result;
import com.miguo.matrix.dto.AdminSearchDto;
import com.miguo.matrix.dto.staff.UpdatePasswordDto;
import com.miguo.matrix.entity.client.Article;
import com.miguo.matrix.entity.client.Swiper;
import com.miguo.matrix.entity.client.Video;
import com.miguo.matrix.entity.media.MediaVideo;
import com.miguo.matrix.entity.staff.Account;
import com.miguo.matrix.service.client.ArticleService;
import com.miguo.matrix.service.client.SwiperService;
import com.miguo.matrix.service.client.VideoService;
import com.miguo.matrix.service.media.MediaVideoService;
import com.miguo.matrix.service.staff.AccountService;
import com.miguo.matrix.utils.SnowflakeIdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Noah
 */
@Api("管理员的接口，该接口管理员工的增删查改")
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private SwiperService swiperService;

    @Autowired
    private MediaVideoService mediaVideoService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    RabbitTemplate rabbitTemplate;

    //    -----------以下为员工信息的增删改查----------

    @ApiOperation(value = "分页查询所有被删除的员工")
    @GetMapping("/fina_all_deleted/{page}/{size}")
    public Result<PageResult<Account>> findAllDeleted(@PathVariable("page") int page, @PathVariable("size") int size) {
        Result<PageResult<Account>> result = new Result<>();
        try {
            Page<Account> accountPage = accountService.findAllDeleted(page, size);
            // 页数减1已经在service层减了
            PageResult<Account> pageResult = new PageResult<>();
            pageResult.setData(accountPage.getContent());
            pageResult.setPage(page).setSize(size).setTotal(accountPage.getTotalElements());
            result.setData(pageResult).setMessage("success").setCode(HttpStatus.OK);
        } catch (Exception e) {
            result.setData(null).setMessage("fail").setCode(HttpStatus.OK);
        }
        return result;
    }

    @ApiOperation(value = "分页查询所有未被删除的员工")
    @GetMapping("/fina_all_exist/{page}/{size}")
    public Result<PageResult<Account>> findAllExist(@PathVariable("page") int page, @PathVariable("size") int size) {
        Result<PageResult<Account>> result = new Result<>();
        try {
            Page<Account> accountPage = accountService.findAllExist(page, size);
            // 页数减1已经在service层减了
            PageResult<Account> pageResult = new PageResult<>();
            pageResult.setData(accountPage.getContent());
            pageResult.setPage(page).setSize(size).setTotal(accountPage.getTotalElements());
            result.setData(pageResult).setMessage("success").setCode(HttpStatus.OK);
        } catch (Exception e) {
            result.setData(null).setMessage("fail").setCode(HttpStatus.OK);
        }

        return result;
    }

    @ApiOperation("分页查找所有模糊查询的员工，接收按时间正逆排序、是否下架")
    @PostMapping("/find_all_by_keywords")
    public Result<PageResult<Account>> findAll(@RequestBody AdminSearchDto searchDto) {
        Result<PageResult<Account>> result = new Result<>();
        Page<Account> page;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                page = accountService.findAllByKeywords("", searchDto.getActive(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
                // 当关键字为空时，查询所有
            } else {
                page = accountService.findAllByKeywords(searchDto.getKeywords(), searchDto.getActive(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<Account> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }

        return result;
    }

    @ApiOperation(value = "通过nickname找员工信息")
    @GetMapping("/find_one_by_nickname/{nickname}")
    public Result<Account> findOneByNickname(@PathVariable("nickname") String nickname) {
        Result<Account> result = new Result<>();
        try {
            Account account = accountService.findOne(nickname);
            result.setCode(HttpStatus.OK).setMessage("success").setData(account);
        } catch (Exception e) {
            result.setCode(HttpStatus.OK).setMessage("fail").setData(null);
        }
        return result;
    }

    @ApiOperation(value = "增加员工")
    @PostMapping("/add")
    public Result<String> add(@RequestBody Account account) {
        Result<String> result = new Result<>();
        try {
            accountService.add(account);
            result.setMessage("add").setData("success").setCode(HttpStatus.OK);

        } catch (Exception e) {
            result.setMessage("add").setData("fail").setCode(HttpStatus.OK);
        }
        return result;
    }

    /**
     * 批量下架
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量软删除员工")
    @GetMapping("/modify/{ids}/{active}")
    public Result<String> delete(@PathVariable("ids") String ids, @PathVariable("active") String active) {
        Result<String> result = new Result<>();
        result.setCode(HttpStatus.OK).setMessage("delete");
        try {
            accountService.modify(ids,active);
            result.setData("success");
        } catch (Exception e) {
            result.setData("fail");
        }
        return result;
    }

    //    -----------以下为文章审核的相关方法----------

    @ApiOperation("分页按条件分类查找删除与否的文章")
    @PostMapping("/article/find_all_class")
    public Result<PageResult<Article>> articleFindAllClass(@RequestBody AdminSearchDto searchDto)
    {
        Result<PageResult<Article>> result = new Result<>();
        Page<Article> pageTemp;
        try {
            if(searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())){
                pageTemp = articleService.findAllClass("", searchDto.getPage(), searchDto.getSize(), searchDto.getActive(), searchDto.getDirection());
            }
            else{
                pageTemp = articleService.findAllClass(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getActive(), searchDto.getDirection());
            }
            PageResult<Article> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(searchDto.getPage()).setSize(searchDto.getSize());
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有的文章")
    @PostMapping("/article/find_all")
    public Result<PageResult<Article>> articleFindAllExist(@RequestBody AdminSearchDto searchDto)
    {
        Result<PageResult<Article>> result = new Result<>();
        Page<Article> pageTemp;
        try {
            if(searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())){
                pageTemp = articleService.findAllExist("", searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            else{
                pageTemp = articleService.findAllExist(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<Article> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(searchDto.getPage()).setSize(searchDto.getSize());
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    //    -----------以下为视频审核的相关方法----------

    @ApiOperation("分页分类查找所有视频")
    @PostMapping("/video/find_all_class")
    public Result<PageResult<Video>> videoFindAllClass(@RequestBody AdminSearchDto searchDto){
        Result<PageResult<Video>> result = new Result<>();
        Page<Video> pageTemp;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                pageTemp = videoService.findAllClass("", searchDto.getPage(), searchDto.getSize(), searchDto.getActive(), searchDto.getDirection());
            }
            else{
                pageTemp = videoService.findAllClass(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getActive(), searchDto.getDirection());
            }
            PageResult<Video> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(searchDto.getPage()).setSize(searchDto.getSize());
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有未被删除的视频")
    @PostMapping("/video/find_all")
    public Result<PageResult<Video>> videoFindAllExist(@RequestBody AdminSearchDto searchDto){
        Result<PageResult<Video>> result = new Result<>();
        Page<Video> pageTemp;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                pageTemp = videoService.findAllExist("", searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            else{
                pageTemp = videoService.findAllExist(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<Video> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(searchDto.getPage()).setSize(searchDto.getSize());
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    //    -----------以下为轮播图审核的相关方法----------

    @ApiOperation("分页分类查找所有视频")
    @PostMapping("/swiper/find_all_class")
    public Result<PageResult<Swiper>> swiperFindAllClass(@RequestBody AdminSearchDto searchDto){
        Result<PageResult<Swiper>> result = new Result<>();
        Page<Swiper> pageTemp;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                pageTemp = swiperService.findAllClass("", searchDto.getPage(), searchDto.getSize(), searchDto.getActive(), searchDto.getDirection());
            }
            else{
                pageTemp = swiperService.findAllClass(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getActive(), searchDto.getDirection());
            }
            PageResult<Swiper> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(searchDto.getPage()).setSize(searchDto.getSize());
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有未被删除的视频")
    @PostMapping("/swiper/find_all")
    public Result<PageResult<Swiper>> swiperFindAllExist(@RequestBody AdminSearchDto searchDto){
        Result<PageResult<Swiper>> result = new Result<>();
        Page<Swiper> pageTemp;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                pageTemp = swiperService.findAllExist("", searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            else{
                pageTemp = swiperService.findAllExist(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<Swiper> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(searchDto.getPage()).setSize(searchDto.getSize());
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    //    -----------以下为视频分发审核的相关方法----------

    @ApiOperation("分页分类查找所有视频")
    @PostMapping("/media_video/find_all_class")
    public Result<PageResult<MediaVideo>> mediaVideoFindAllClass(@RequestBody AdminSearchDto searchDto){
        Result<PageResult<MediaVideo>> result = new Result<>();
        Page<MediaVideo> pageTemp;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                pageTemp = mediaVideoService.findAllClass("", searchDto.getPage(), searchDto.getSize(), searchDto.getActive(), searchDto.getDirection());
            }
            else{
                pageTemp = mediaVideoService.findAllClass(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getActive(), searchDto.getDirection());
            }
            PageResult<MediaVideo> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(searchDto.getPage()).setSize(searchDto.getSize());
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有未被删除的视频")
    @PostMapping("/media_video/find_all")
    public Result<PageResult<MediaVideo>> mediaVideoFindAllExist(@RequestBody AdminSearchDto searchDto){
        Result<PageResult<MediaVideo>> result = new Result<>();
        Page<MediaVideo> pageTemp;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                pageTemp = mediaVideoService.findAllExist("", searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            else{
                pageTemp = mediaVideoService.findAllExist(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<MediaVideo> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(searchDto.getPage()).setSize(searchDto.getSize());
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("视频的分发+更新")
    @PutMapping("/media_video/distribution")
    public Result<String> mediaVideoDistribution(@RequestBody MediaVideo mediaVideo){
        Result<String> result = new Result<>();
        try{

            String messageId = snowflakeIdWorker.nextId();
            Date createTime = new Date();
            Map<String, Object> map=new HashMap<>(16);
            map.put("messageId",messageId);
            map.put("messageData",mediaVideo);
            map.put("createTime",createTime);
            //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
            rabbitTemplate.convertAndSend("videoSendingExchange", "videoSendingRouting", map);

            mediaVideoService.update(mediaVideo);
            result.setCode(HttpStatus.OK).setMessage("update").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("update").setData("fail");
        }
        return result;
    }

}
