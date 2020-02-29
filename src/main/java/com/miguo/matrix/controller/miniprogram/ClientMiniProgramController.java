package com.miguo.matrix.controller.miniprogram;

import com.miguo.matrix.dto.Result;
import com.miguo.matrix.entity.miniprogram.*;
import com.miguo.matrix.service.miniprogram.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Hocassian
 */
@Api("小程序专用接口，无需登陆")
@Slf4j
@RestController
@RequestMapping("/client_miniprogram")
public class ClientMiniProgramController {
    /*
     * 需要实现：
     * 1.获取当前投票活动
     * 2.获取当前活动的投票对象
     * 3.记录每一条投票记录（增加）
     * 4.获取投票记录条目数
     * 5.获取对应的轮播图、赞助商等信息
     *
     * 注意：我目前的逻辑是，先让小程序前端随便发个值过来，返回一个目前激活的活动id，然后小程序保存这个id为全局变量，每次都返回这个id来获取其他的参数。
     * 实际上甚至可以把这些全部放在后端来完成，前端随便返回什么值，后端都按照当前激活的活动来返回对应的对象。
     * 总之无伤大雅了，运行效率都差不多。
     * */

    private static final String IS_VOTED = "0";

    @Autowired
    private MpSwiperService swiperService;

    @Autowired
    private MpActivityService activityService;

    @Autowired
    private MpGroupService groupService;

    @Autowired
    private MpRecordService recordService;

    @Autowired
    private MpMerchantService merchantService;

    @Autowired
    private MpNoteService noteService;

    @ApiOperation("查找活躍的活動")
    @PostMapping("/activity/find_active")
    public Result<List<Activity>> findActiveActivity(String a) {
        Result<List<Activity>> result = new Result<>();
        List<Activity> list;
        try {
            list = activityService.findActiveOne();
            result.setMessage("success").setCode(HttpStatus.OK).setData(list);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    @ApiOperation("查找活躍的投票对象")
    @GetMapping("/group/find_active/{id}")
    public Result<List<Group>> findActiveGroup(@PathVariable("id") String id) {
        Result<List<Group>> result = new Result<>();
        List<Group> list;
        try {
            list = groupService.findActiveOne(id);
            result.setMessage("success").setCode(HttpStatus.OK).setData(list);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    @ApiOperation("查找活躍的轮播图")
    @GetMapping("/swiper/find_active/{id}")
    public Result<List<Swiper>> findActiveSwiper(@PathVariable("id") String id) {
        Result<List<Swiper>> result = new Result<>();
        List<Swiper> list;
        try {
            list = swiperService.findActiveOne(id);
            result.setMessage("success").setCode(HttpStatus.OK).setData(list);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    @ApiOperation("查找活躍的赞助商")
    @GetMapping("/merchant/find_active/{id}")
    public Result<List<Merchant>> findActiveMerchant(@PathVariable("id") String id) {
        Result<List<Merchant>> result = new Result<>();
        List<Merchant> list;
        try {
            list = merchantService.findActiveOne(id);
            result.setMessage("success").setCode(HttpStatus.OK).setData(list);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    @ApiOperation("根据时间查找活躍的公告")
    @GetMapping("/note/find_active/{id}")
    public Result<List<Note>> findActiveNote(@PathVariable("id") String id) {
        Result<List<Note>> result = new Result<>();
        List<Note> list;
        try {
            list = noteService.findActiveOne(id);
            result.setMessage("success").setCode(HttpStatus.OK).setData(list);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    @ApiOperation("为投票对象投票，传回对象id和投票人的openid，通过接口综合比对，杜绝刷票行为")
    @PostMapping("/record/add")
    public Result<String> voteGroup(@RequestBody Record record) {
        Result<String> result = new Result<>();
        result.setCode(HttpStatus.OK).setMessage("vote");
        try {
            String isVoted = recordService.checkIsVoted(record);
            if(IS_VOTED.equals(isVoted)){
                recordService.add(record);
                groupService.vote(record.getGroupId());
                result.setData("success");
            }
            else{
                result.setData("fail");
            }
        } catch (Exception e) {
            result.setData("fail");
        }
        return result;
    }
}
