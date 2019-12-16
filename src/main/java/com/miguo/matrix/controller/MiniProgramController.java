package com.miguo.matrix.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("小程序专用接口，无需登陆")
@Slf4j
@RestController
@RequestMapping("/miniprogram")
public class MiniProgramController {
    /*
    * 需要实现：
    * 1.投票活动
    * 2.对于每个活动创建投票对象
    * 3.记录每一条投票记录
    * 4.
    * */


}
