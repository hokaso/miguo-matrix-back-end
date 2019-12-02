package com.miguo.matrix.controller;

import com.miguo.matrix.dto.Result;
import com.miguo.matrix.entity.staff.Account;
import com.miguo.matrix.service.staff.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2019-11-27 17:04
 */

@Api("登陆接口")
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController  {

    @Autowired
    private AccountService accountService;

    /**
     * 系统登录
     */
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result<Account> login(@RequestBody Account account){

        if(!accountService.exist(account.getNickname())){
            return new Result(HttpStatus.OK, "用户不存在！", null);
        }else {
            if(account.getPassword().equals(accountService.password(account.getNickname()))){
                return new Result(HttpStatus.OK, "登陆成功！", null);
            }else {
                return new Result(HttpStatus.OK, "密码错误！", null);
            }
        }
    }
}
