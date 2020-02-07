package com.miguo.matrix.controller.miniprogram;

import com.miguo.matrix.dto.PageResult;
import com.miguo.matrix.dto.Result;
import com.miguo.matrix.dto.SearchDto;
import com.miguo.matrix.entity.miniprogram.*;
import com.miguo.matrix.service.miniprogram.*;
import com.miguo.matrix.vo.miniprogram.GroupVo;
import com.miguo.matrix.vo.miniprogram.MerchantVo;
import com.miguo.matrix.vo.miniprogram.NoteVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Hocassian
 */

@Api("员工添加小程序相关数据的接口")
@Slf4j
@RestController
@RequestMapping("/staff_miniprogram")
public class StaffMiniProgramController {

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

    // ----------以下为小程序轮播图的增删查改-------

    @ApiOperation("小程序轮播图的添加")
    @PostMapping("/mp_swiper/add")
    public Result<String> mpSwiperAdd(@RequestBody Swiper swiper){
        Result<String> result = new Result<>();
        try{
            swiperService.add(swiper);
            result.setCode(HttpStatus.OK).setMessage("add").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("add").setData("fail");
        }
        return result;
    }

    @ApiOperation("小程序轮播图的批量软删除")
    @PostMapping("/mp_swiper/delete")
    public Result<String> mpSwiperDelete(@RequestBody List<Swiper> list) {
        Result<String> result =new Result<>();
        try{
            swiperService.delete(list);
            result.setCode(HttpStatus.OK).setMessage("delete").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("delete").setData("fail");
        }
        return result;
    }

    @ApiOperation("小程序轮播图的更新")
    @PutMapping("/mp_swiper/update")
    public Result<String> mpSwiperUpdate(@RequestBody Swiper swiper){
        Result<String> result = new Result<>();
        try{
            swiperService.update(swiper);
            result.setCode(HttpStatus.OK).setMessage("update").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("update").setData("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有标题或者内容包含该关键字的小程序轮播图（录入活动页面用,「keywords」为空时返回所有）")
    @PostMapping("/mp_swiper/find_all_by_keywords")
    public Result<PageResult<Swiper>> mpSwiperFindAll(@RequestBody SearchDto searchDto) {
        Result<PageResult<Swiper>> result = new Result<>();
        Page<Swiper> page;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                page = swiperService.findSwiperByKeywords("", searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            } else {
                page = swiperService.findSwiperByKeywords(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<Swiper> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    // ----------以下为小程序活动的增删查改-------

    @ApiOperation("小程序活动的添加")
    @PostMapping("/mp_activity/add")
    public Result<String> mpActivityAdd(@RequestBody Activity activity){
        Result<String> result = new Result<>();
        try{
            activityService.add(activity);
            result.setCode(HttpStatus.OK).setMessage("add").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("add").setData("fail");
        }
        return result;
    }

    @ApiOperation("小程序活动的批量删除")
    @PostMapping("/mp_activity/delete")
    public Result<String> mpActivityDelete(@RequestBody List<Activity> list) {
        Result<String> result =new Result<>();
        try{
            activityService.delete(list);
            result.setCode(HttpStatus.OK).setMessage("delete").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("delete").setData("fail");
        }
        return result;
    }

    @ApiOperation("小程序活动的更新")
    @PutMapping("/mp_activity/update")
    public Result<String> mpActivityUpdate(@RequestBody Activity activity){
        Result<String> result = new Result<>();
        try{
            activityService.update(activity);
            result.setCode(HttpStatus.OK).setMessage("update").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("update").setData("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有标题或者内容包含该关键字的活动（录入活动页面用,「keywords」为空时返回所有）")
    @PostMapping("/mp_activity/find_all_by_keywords")
    public Result<PageResult<Activity>> mpActivityFindAll(@RequestBody SearchDto searchDto) {
        Result<PageResult<Activity>> result = new Result<>();
        Page<Activity> page;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                page = activityService.findAllActivityByKeywords("", searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            } else {
                page = activityService.findAllActivityByKeywords(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<Activity> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    @ApiOperation("不分页查找所有标题或者内容包含该关键字的活动")
    @GetMapping("/mp_activity/find_all_by_keywords_from_input/{keyword}")
    public Result<List<Activity>> mpActivityFindAllFromInput(@PathVariable("keyword") String keywords) {
        Result<List<Activity>> result = new Result<>();
        try {
            List<Activity> list;
            if (keywords == null || "".equals(keywords)) {
                list = activityService.findAllActivityByKeywordsFromInput("");
            } else {
                list = activityService.findAllActivityByKeywordsFromInput(keywords);
            }
            result.setMessage("success").setCode(HttpStatus.OK).setData(list);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    @ApiOperation("用于返回该表联结的值")
    @GetMapping("/mp_activity/find_activity_id/{id}")
    public Result<Optional<Activity>> findById(@PathVariable("id") String id){
        Optional<Activity> list= activityService.findById(id);
        Result<Optional<Activity>> result =new Result<>();
        result.setCode(HttpStatus.OK).setData(list).setMessage("success");
        return result;
    }

    // ----------以下为小程序投票对象的增删查改-------

    @ApiOperation("小程序投票对象的添加")
    @PostMapping("/mp_group/add")
    public Result<String> mpGroupAdd(@RequestBody GroupVo groupVo){
        Result<String> result = new Result<>();
        try{
            groupService.add(groupVo);
            result.setCode(HttpStatus.OK).setMessage("add").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("add").setData("fail");
        }
        return result;
    }

    @ApiOperation("小程序投票对象的批量软删除")
    @PostMapping("/mp_group/delete")
    public Result<String> mpGroupDelete(@RequestBody List<Group> list) {
        Result<String> result =new Result<>();
        try{
            groupService.delete(list);
            result.setCode(HttpStatus.OK).setMessage("delete").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("delete").setData("fail");
        }
        return result;
    }

    @ApiOperation("小程序投票对象的更新")
    @PutMapping("/mp_group/update")
    public Result<String> mpGroupUpdate(@RequestBody Group group){
        Result<String> result = new Result<>();
        try{
            groupService.update(group);
            result.setCode(HttpStatus.OK).setMessage("update").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("update").setData("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有标题或者内容包含该关键字的小程序投票对象（录入活动页面用,「keywords」为空时返回所有）")
    @PostMapping("/mp_group/find_all_by_keywords")
    public Result<PageResult<GroupVo>> mpGroupFindAll(@RequestBody SearchDto searchDto) {
        Result<PageResult<GroupVo>> result = new Result<>();
        Page<GroupVo> page;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                page = groupService.findGroupByKeywords("", searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            } else {
                page = groupService.findGroupByKeywords(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<GroupVo> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            System.out.print(e);
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    // ----------以下为小程序投票记录的增删查改-------

    @ApiOperation("小程序投票记录的添加")
    @PostMapping("/mp_record/add")
    public Result<String> mpRecordAdd(@RequestBody Record record){
        Result<String> result = new Result<>();
        try{
            recordService.add(record);
            result.setCode(HttpStatus.OK).setMessage("add").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("add").setData("fail");
        }
        return result;
    }

    @ApiOperation("小程序投票记录的批量软删除")
    @PostMapping("/mp_record/delete")
    public Result<String> mpRecordDelete(@RequestBody List<Record> list) {
        Result<String> result =new Result<>();
        try{
            recordService.delete(list);
            result.setCode(HttpStatus.OK).setMessage("delete").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("delete").setData("fail");
        }
        return result;
    }

    @ApiOperation("小程序投票记录的更新")
    @PutMapping("/mp_record/update")
    public Result<String> mpRecordUpdate(@RequestBody Record record){
        Result<String> result = new Result<>();
        try{
            recordService.update(record);
            result.setCode(HttpStatus.OK).setMessage("update").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("update").setData("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有标题或者内容包含该关键字的小程序投票记录（录入活动页面用,「keywords」为空时返回所有）")
    @PostMapping("/mp_record/find_all_by_keywords")
    public Result<PageResult<Record>> mpRecordFindAll(@RequestBody SearchDto searchDto) {
        Result<PageResult<Record>> result = new Result<>();
        Page<Record> page;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                page = recordService.findRecordByKeywords("", searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            } else {
                page = recordService.findRecordByKeywords(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<Record> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    // ----------以下为小程序赞助商的增删查改-------

    @ApiOperation("小程序赞助商的添加")
    @PostMapping("/mp_merchant/add")
    public Result<String> mpMerchantAdd(@RequestBody Merchant merchant){
        Result<String> result = new Result<>();
        try{
            merchantService.add(merchant);
            result.setCode(HttpStatus.OK).setMessage("add").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("add").setData("fail");
        }
        return result;
    }

    @ApiOperation("小程序赞助商的批量删除")
    @PostMapping("/mp_merchant/delete")
    public Result<String> mpMerchantDelete(@RequestBody List<Merchant> list) {
        Result<String> result =new Result<>();
        try{
            merchantService.delete(list);
            result.setCode(HttpStatus.OK).setMessage("delete").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("delete").setData("fail");
        }
        return result;
    }

    @ApiOperation("小程序赞助商的更新")
    @PutMapping("/mp_merchant/update")
    public Result<String> mpMerchantUpdate(@RequestBody Merchant merchant){
        Result<String> result = new Result<>();
        try{
            merchantService.update(merchant);
            result.setCode(HttpStatus.OK).setMessage("update").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("update").setData("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有标题或者内容包含该关键字的小程序赞助商（录入活动页面用,「keywords」为空时返回所有）")
    @PostMapping("/mp_merchant/find_all_by_keywords")
    public Result<PageResult<MerchantVo>> mpMerchantFindAll(@RequestBody SearchDto searchDto) {
        Result<PageResult<MerchantVo>> result = new Result<>();
        Page<MerchantVo> page;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                page = merchantService.findMerchantByKeywords("", searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            } else {
                page = merchantService.findMerchantByKeywords(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<MerchantVo> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            System.out.print(e);
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    // ----------以下为小程序公告栏的增删查改-------

    @ApiOperation("小程序公告栏的添加")
    @PostMapping("/mp_note/add")
    public Result<String> mpNoteAdd(@RequestBody Note note){
        Result<String> result = new Result<>();
        try{
            noteService.add(note);
            result.setCode(HttpStatus.OK).setMessage("add").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("add").setData("fail");
        }
        return result;
    }

    @ApiOperation("小程序公告栏的批量软删除")
    @PostMapping("/mp_note/delete")
    public Result<String> mpNoteDelete(@RequestBody List<Note> list) {
        Result<String> result =new Result<>();
        try{
            noteService.delete(list);
            result.setCode(HttpStatus.OK).setMessage("delete").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("delete").setData("fail");
        }
        return result;
    }

    @ApiOperation("小程序公告栏的更新")
    @PutMapping("/mp_note/update")
    public Result<String> mpNoteUpdate(@RequestBody Note note){
        Result<String> result = new Result<>();
        try{
            noteService.update(note);
            result.setCode(HttpStatus.OK).setMessage("update").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("update").setData("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有标题或者内容包含该关键字的小程序公告栏（录入活动页面用,「keywords」为空时返回所有）")
    @PostMapping("/mp_note/find_all_by_keywords")
    public Result<PageResult<NoteVo>> mpNoteFindAll(@RequestBody SearchDto searchDto) {
        Result<PageResult<NoteVo>> result = new Result<>();
        Page<NoteVo> page;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                page = noteService.findNoteByKeywords("", searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            } else {
                page = noteService.findNoteByKeywords(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<NoteVo> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }
}
