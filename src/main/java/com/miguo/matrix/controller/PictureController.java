package com.miguo.matrix.controller;

import com.miguo.matrix.utils.SnowflakeIdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Api("专门用来上传图片")
@RequestMapping("/picture")
@RestController
public class PictureController {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Value("${file.path}")
    private String filePath;
    // 上传的路径

    @ApiOperation("上传图片专用接口")
    @PostMapping("/upload")
    public String test(MultipartFile file){

        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名

        fileName = snowflakeIdWorker.nextId() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            // 若不存在该文件夹，则创建一个
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = "http://101.37.172.123:9090/file/" + fileName;//返回的文件URL

        System.out.println("返回的文件URL：" + filename);
        return filename;
    }
}
