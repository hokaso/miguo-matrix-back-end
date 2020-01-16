package com.miguo.matrix.controller;

import com.miguo.matrix.dto.Result;
import com.miguo.matrix.utils.SnowflakeIdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Hocassian
 */
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
    @CrossOrigin
    public Result<String> upload(@RequestParam(value = "file") MultipartFile file){

        Result<String> result = new Result<>();
        // 文件名
        String fileName = file.getOriginalFilename();
        // 后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 新文件名
        fileName = snowflakeIdWorker.nextId() + suffixName;
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
        System.out.println("返回的文件URL：" + fileName);
        return result.setData(fileName).setMessage("已经注销！").setCode(HttpStatus.OK);
    }
}
