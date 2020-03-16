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
import java.util.Enumeration;

/**
 * 功能描述：
 *
 * @author Hocassian
 * @date 2019-11-27 17:04
 */

@Api("登陆接口")
@Slf4j
@RestController
@RequestMapping("/account")
public class LoginController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private HttpSession session;

    private static final String STAFF = "2";
    private static final String ADMIN = "1";
    /**
     * 系统登录
     */
    @ApiOperation(value = "系统登录")
    @PostMapping("/login")
    public Result<String> login(@RequestBody Account account, HttpServletRequest request) {
        Account accountTemp = accountService.findOne(account.getNickname());
        Result<String> result = new Result<>();
//        HttpSession session = request.getSession;
        if (!accountService.isExistByNickname(account.getNickname())) {

            result.setData("fail").setMessage("用户不存在！").setCode(HttpStatus.OK);
        } else {
            if (account.getPassword().equals((accountTemp.getPassword()))) {
                // 管理员登陆
                if(accountTemp.getLevel().equals(ADMIN)){
                    result.setData("admin").setMessage("登陆成功！").setCode(HttpStatus.OK);
                    // 200分钟有效期
                    session.setMaxInactiveInterval(1000 * 60 * 200);
                    request.getSession().setAttribute("user", account.getNickname());
                    System.out.print((String) session.getAttribute("user"));
                }
                // 普通员工登陆
                else {
                    result.setData("staff").setMessage("登陆成功！").setCode(HttpStatus.OK);
                    // 100分钟有效期
                    session.setMaxInactiveInterval(1000 * 60 * 100);
                    request.getSession().setAttribute("user", account.getNickname());
                }
            } else {
                result.setData("fail").setMessage("密码错误！").setCode(HttpStatus.OK);
            }
        }
        return result;
    }

    @ApiOperation(value = "系统注销")
    @PostMapping("/logout")
    public Result<String> logout() {
        Result<String> result = new Result<>();
        Enumeration em = session.getAttributeNames();
        while (em.hasMoreElements()) {
            session.removeAttribute(em.nextElement().toString());
        }
        return result.setData("logout").setMessage("已经注销！").setCode(HttpStatus.OK);
    }
}
