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
    * 2.查单个员工
    * 3.查全部查员工
    * */
    @Autowired
    private AccountService accountService;

    /***************新增员工模块*************/
    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public Result<String> add(@RequestBody Account account) {
        accountService.add(account);
        return new Result(HttpStatus.OK, "新增成功！", "success");
    }

    /***************删除员工模块*************/
    @ApiOperation(value = "删除")
    @DeleteMapping("/del")
    public Result<String> del(String id) {
        accountService.del(id);
        return new Result(HttpStatus.OK, "删除成功！", "success");
    }

    /***************查询员工模块*************/
    @ApiOperation(value = "分页查询所有员工")
    @PostMapping("/find/{page}/{size}")
    public Result<Account> selectAll(@PathVariable("page") Integer pageNumber, @PathVariable("size") Integer pageSize) {
        Page page = accountService.select(pageNumber, pageSize);
        PageResult<Account> pageResult = new PageResult(page.getNumber() + 1, page.getSize(), page.getTotalElements(), page.getContent());
        Result<Account> result = new Result(HttpStatus.OK, "success", pageResult);
        return result;
    }

    @ApiOperation(value = "查找某一个员工")
    public Result<Account> selectOne() {
        Result<Account> result = new Result<>();
        result.setCode(HttpStatus.OK).setMessage("success").setData(null);
        return result;

    }

    /***************修改员工模块*************/
    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public Result<String> update(@RequestBody Account account) {
        accountService.update(account);
        return new Result(HttpStatus.OK, "更新成功！", "success");
    }

}
