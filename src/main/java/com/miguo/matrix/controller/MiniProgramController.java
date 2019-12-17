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
    * 1.获取当前投票活动
    * 2.获取当前活动的投票对象
    * 3.记录每一条投票记录（增加）
    * 4.获取投票记录条目数
    * 5.获取对应的轮播图、赞助商等信息
    * */


}
