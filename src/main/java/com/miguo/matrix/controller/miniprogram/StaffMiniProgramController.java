package com.miguo.matrix.controller.miniprogram;

import com.miguo.matrix.dto.PageResult;
import com.miguo.matrix.dto.Result;
import com.miguo.matrix.dto.SearchDto;
import com.miguo.matrix.entity.miniprogram.*;
import com.miguo.matrix.service.miniprogram.*;
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
    @DeleteMapping("/mp_swiper/delete/{ids}")
    public Result<String> mpSwiperDelete(@PathVariable("ids") String ids) {
        Result<String> result =new Result<>();
        try{
            swiperService.delete(ids);
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

    @ApiOperation("查找所有已被删除的小程序轮播图")
    @GetMapping("/mp_swiper/find_all_deleted/{page}/{size}")
    public Result<PageResult<Swiper>> mpSwiperFindAllDeleted(@PathVariable("page") int page, @PathVariable("size") int size){
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

    @ApiOperation("查找所有未被删除的小程序轮播图")
    @GetMapping("/mp_swiper/find_all_exist/{page}/{size}")
    public Result<PageResult<Swiper>> mpSwiperFindAllExist(@PathVariable("page") int page, @PathVariable("size") int size){
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

    @ApiOperation("小程序活动的批量软删除")
    @DeleteMapping("/mp_activity/delete/{ids}")
    public Result<String> mpActivityDelete(@PathVariable("ids") String ids) {
        Result<String> result =new Result<>();
        try{
            activityService.delete(ids);
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

    @ApiOperation("分页查找所有已被删除的小程序活动")
    @GetMapping("/mp_activity/find_all_deleted/{page}/{size}")
    public Result<PageResult<Activity>> mpActivityFindAllDeleted(@PathVariable("page") int page, @PathVariable("size") int size){
        Result<PageResult<Activity>> result = new Result<>();
        Page<Activity> pageTemp;
        try {
            pageTemp = activityService.findAllDeleted(page, size);
            PageResult<Activity> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有未被删除的小程序活动")
    @GetMapping("/mp_activity/find_all_exist/{page}/{size}")
    public Result<PageResult<Activity>> mpActivityFindAllExist(@PathVariable("page") int page, @PathVariable("size") int size){
        Result<PageResult<Activity>> result = new Result<>();
        Page<Activity> pageTemp;
        try {
            pageTemp = activityService.findAllExist(page, size);
            PageResult<Activity> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有标题或者内容包含该关键字的未被删除的活动")
    @PostMapping("/mp_activity/find_all_exist_by_keyowrds")
    public Result<PageResult<Activity>> mpActivityFindAll(@RequestBody SearchDto searchDto) {
        Result<PageResult<Activity>> result = new Result<>();
        Page<Activity> page;
        try {
            if (searchDto.getKeywords() == null || searchDto.getKeywords().equals("")) {
                page = activityService.findAllActivityByKeywords("", searchDto.getPage(), searchDto.getSize());
                // 当关键字为空时，查询所有
            } else {
                page = activityService.findAllActivityByKeywords(searchDto.getKeywords(), searchDto.getPage(), searchDto.getSize());
            }
            PageResult<Activity> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }
        return result;
    }

    @ApiOperation("不分页查找所有标题或者内容包含该关键字的未被删除的活动")
    @PostMapping("/mp_activity/find_all_exist_by_keyowrds_from_input")
    public Result<List<Activity>> mpActivityFindAllFromInput(@RequestBody String keywords) {
        Result<List<Activity>> result = new Result<>();
        try {
            List<Activity> list;
            if (keywords == null || keywords.equals("")) {
                // 当关键字为空时，查询所有
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

    // ----------以下为小程序投票对象的增删查改-------

    @ApiOperation("小程序投票对象的添加")
    @PostMapping("/mp_group/add")
    public Result<String> mpGroupAdd(@RequestBody Group group){
        Result<String> result = new Result<>();
        try{
            groupService.add(group);
            result.setCode(HttpStatus.OK).setMessage("add").setData("success");
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("add").setData("fail");
        }
        return result;
    }

    @ApiOperation("小程序投票对象的批量软删除")
    @DeleteMapping("/mp_group/delete/{ids}")
    public Result<String> mpGroupDelete(@PathVariable("ids") String ids) {
        Result<String> result =new Result<>();
        try{
            groupService.delete(ids);
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

    @ApiOperation("查找所有已被删除的小程序投票对象")
    @GetMapping("/mp_group/find_all_deleted/{page}/{size}")
    public Result<PageResult<Group>> mpGroupFindAllDeleted(@PathVariable("page") int page, @PathVariable("size") int size){
        Result<PageResult<Group>> result = new Result<>();
        Page<Group> pageTemp;
        try {
            pageTemp = groupService.findAllDeleted(page, size);
            PageResult<Group> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("查找所有未被删除的小程序投票对象")
    @GetMapping("/mp_group/find_all_exist/{page}/{size}")
    public Result<PageResult<Group>> mpGroupFindAllExist(@PathVariable("page") int page, @PathVariable("size") int size){
        Result<PageResult<Group>> result = new Result<>();
        Page<Group> pageTemp;
        try {
            pageTemp = groupService.findAllExist(page, size);
            PageResult<Group> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
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
    @DeleteMapping("/mp_record/delete/{ids}")
    public Result<String> mpRecordDelete(@PathVariable("ids") String ids) {
        Result<String> result =new Result<>();
        try{
            recordService.delete(ids);
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

    @ApiOperation("查找所有已被删除的小程序投票记录")
    @GetMapping("/mp_record/find_all_deleted/{page}/{size}")
    public Result<PageResult<Record>> mpRecordFindAllDeleted(@PathVariable("page") int page, @PathVariable("size") int size){
        Result<PageResult<Record>> result = new Result<>();
        Page<Record> pageTemp;
        try {
            pageTemp = recordService.findAllDeleted(page, size);
            PageResult<Record> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("查找所有未被删除的小程序投票记录")
    @GetMapping("/mp_record/find_all_exist/{page}/{size}")
    public Result<PageResult<Record>> mpRecordFindAllExist(@PathVariable("page") int page, @PathVariable("size") int size){
        Result<PageResult<Record>> result = new Result<>();
        Page<Record> pageTemp;
        try {
            pageTemp = recordService.findAllExist(page, size);
            PageResult<Record> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
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

    @ApiOperation("小程序赞助商的批量软删除")
    @DeleteMapping("/mp_merchant/delete/{ids}")
    public Result<String> mpMerchantDelete(@PathVariable("ids") String ids) {
        Result<String> result =new Result<>();
        try{
            merchantService.delete(ids);
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

    @ApiOperation("查找所有已被删除的小程序赞助商")
    @GetMapping("/mp_merchant/find_all_deleted/{page}/{size}")
    public Result<PageResult<Merchant>> mpMerchantFindAllDeleted(@PathVariable("page") int page, @PathVariable("size") int size){
        Result<PageResult<Merchant>> result = new Result<>();
        Page<Merchant> pageTemp;
        try {
            pageTemp = merchantService.findAllDeleted(page, size);
            PageResult<Merchant> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("查找所有未被删除的小程序赞助商")
    @GetMapping("/mp_merchant/find_all_exist/{page}/{size}")
    public Result<PageResult<Merchant>> mpMerchantFindAllExist(@PathVariable("page") int page, @PathVariable("size") int size){
        Result<PageResult<Merchant>> result = new Result<>();
        Page<Merchant> pageTemp;
        try {
            pageTemp = merchantService.findAllExist(page, size);
            PageResult<Merchant> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
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
    @DeleteMapping("/mp_note/delete/{ids}")
    public Result<String> mpNoteDelete(@PathVariable("ids") String ids) {
        Result<String> result =new Result<>();
        try{
            noteService.delete(ids);
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

    @ApiOperation("查找所有已被删除的小程序公告栏")
    @GetMapping("/mp_note/find_all_deleted/{page}/{size}")
    public Result<PageResult<Note>> mpNoteFindAllDeleted(@PathVariable("page") int page, @PathVariable("size") int size){
        Result<PageResult<Note>> result = new Result<>();
        Page<Note> pageTemp;
        try {
            pageTemp = noteService.findAllDeleted(page, size);
            PageResult<Note> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("查找所有未被删除的小程序公告栏")
    @GetMapping("/mp_note/find_all_exist/{page}/{size}")
    public Result<PageResult<Note>> mpNoteFindAllExist(@PathVariable("page") int page, @PathVariable("size") int size){
        Result<PageResult<Note>> result = new Result<>();
        Page<Note> pageTemp;
        try {
            pageTemp = noteService.findAllExist(page, size);
            PageResult<Note> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }
}
