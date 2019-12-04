package com.miguo.matrix.controller;

import com.miguo.matrix.dto.PageResult;
import com.miguo.matrix.dto.Result;
import com.miguo.matrix.entity.staff.Account;
import com.miguo.matrix.service.staff.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Hocassian
 */
@Api("员工接口，需要登录")
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {
    /*
     * 需要实现：
     * 1.增删改员工
     * */
    @Autowired
    private AccountService accountService;

    // 分页查询所有员工
    @ApiOperation(value = "分页查询所有员工")
    @GetMapping("/findAll/{page}/{size}")
    public Result<PageResult<Account>> findAll(@PathVariable("page") int page, @PathVariable("size") int size) {
        Result<PageResult<Account>> result = new Result<>();
        page--;
        try {
            Page<Account> accountPage = accountService.findAll(page, size);
            PageResult<Account> pageResult = new PageResult<>();
            pageResult.setData(accountPage.getContent());
            page++;
            pageResult.setPage(page).setSize(size).setTotal(accountPage.getTotalElements());
            result.setData(pageResult).setMessage("success").setCode(HttpStatus.OK);
        } catch (Exception e) {
            result.setData(null).setMessage("fail").setCode(HttpStatus.OK);
        }

        return result;
    }

    // 找某一个员工的信息
    @ApiOperation(value = "通过nickname找员工信息")
    @GetMapping("/findOne/{nickname}")
    public Result<Account> findOne(@PathVariable("nickname") String nickname) {
        Result<Account> result = new Result<>();
        try {
            Account account = accountService.findOne(nickname);
            result.setCode(HttpStatus.OK).setMessage("success").setData(account);
        } catch (Exception e) {
            result.setCode(HttpStatus.OK).setMessage("fail").setData(null);
        }
        return result;
    }

    @ApiOperation(value = "增加员工")
    @PostMapping("/add")
    public Result<String> add(@RequestBody Account account) {
        Result<String> result = new Result<>();
        result.setCode(HttpStatus.OK);
        try {
            accountService.add(account);
            result.setMessage("success").setData("success");

        } catch (Exception e) {
            result.setMessage("fail").setData("fail");
        }
        return result;
    }

    @ApiOperation(value = "修改员工名称")
    @PutMapping("/update_name")
    public Result<String> updateName(@RequestBody String name) {
        System.out.println(name);

        return null;
    }

    @ApiOperation(value = "删除某个员工")
    @DeleteMapping("/delete/{nickname}")
    public Result<String> delete(@PathVariable("nickname") String nickname) {
        return null;
    }


}
