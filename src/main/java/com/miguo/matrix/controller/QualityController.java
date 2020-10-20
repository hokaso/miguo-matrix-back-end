package com.miguo.matrix.controller;

import com.miguo.matrix.dto.Result;
import com.miguo.matrix.repository.staff.QualityRepository;
import com.miguo.matrix.service.staff.CountService;
import com.miguo.matrix.vo.CountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2020-03-25 14:28
 */
@Api("统计专用接口")
@Slf4j
@RestController
@RequestMapping("/count")
public class QualityController {

    @Autowired
    private CountService countService;

    @ApiOperation("统计所有数据")
    @PostMapping("/quality")
    public Result<Map<String, Integer>> findAllCount(String a) {
        Result<Map<String, Integer>> result = new Result<>();
        Map<String, Integer> map;
        try {
            map = countService.findAllCount();
            result.setMessage("success").setCode(HttpStatus.OK).setData(map);
        }catch (Exception e){
            result.setCode(HttpStatus.OK).setMessage("fail").setData(null);
        }
        return result;
    }
}
