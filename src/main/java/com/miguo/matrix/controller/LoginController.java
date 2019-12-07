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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2019-11-27 17:04
 */

@Api("登陆接口")
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AccountService accountService;

    /**
     * 系统登录
     */
    @ApiOperation(value = "普通员工登录")
    @PostMapping("/account")
    public Result<String> login(@RequestBody Account account, HttpServletRequest request) {
        Account account1 = accountService.findOne(account.getNickname());
        Result<String> result = new Result<>();
        if (account1.getPassword().equals(account.getPassword())) {
            // 登录成功
            result.setData("success").setMessage("login").setCode(HttpStatus.OK);
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(1000*60*10); // 10分钟有效期
            session.setAttribute("staff",account.getNickname());
        } else {
           // 登录失败
            result.setData("fail").setMessage("login").setCode(HttpStatus.OK);
        }
        return result;
    }

    // 普通员工登录
}
