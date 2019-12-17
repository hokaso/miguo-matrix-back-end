package com.miguo.matrix.controller.miniprogram;

import com.miguo.matrix.dto.PageResult;
import com.miguo.matrix.dto.Result;
import com.miguo.matrix.entity.miniprogram.Swiper;
import com.miguo.matrix.service.miniprogram.MpSwiperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    
// ----------以下为小程序轮播图的增删改-------

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
}
