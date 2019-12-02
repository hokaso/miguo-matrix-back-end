package com.miguo.matrix.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("用户专用接口，无需登陆")
@Slf4j
@RestController
@RequestMapping("/client")
public class ClientController {
    /*
    * 需要实现：
    * 1.查文章(标题和正文)、视频(标题)
    * 2.查询所有文章/视频（分页）
    * 3.根据最新时间给4篇文章和视频
    * 4.
    *
    * */
}
