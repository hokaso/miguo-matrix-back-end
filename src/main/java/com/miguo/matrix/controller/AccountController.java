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

/**
 * @author Hocassian
 */
@Api("员工接口")
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController  {

    @Autowired
    private AccountService accountService;

    /***************新增员工模块*************/
    @ApiOperation(value = "新增")
    @PostMapping("/add")
    public Result<Account> add(@RequestBody Account account) {
        accountService.add(account);
        return new Result(HttpStatus.OK, "新增成功！", null);
    }

    /***************删除员工模块*************/
    @ApiOperation(value = "删除")
    @DeleteMapping("/del")
    public Result<Account> del(String id) {
        accountService.del(id);
        return new Result(HttpStatus.OK, "删除成功！", null);
    }

    /***************查询员工模块*************/
    @ApiOperation(value = "查询")
    @PostMapping("/find/{page}/{size}")
    public Result<Account> select(@PathVariable("page") Integer pageNumber, @PathVariable("size")  Integer pageSize) {
        Page page=accountService.select(pageNumber,pageSize);
        PageResult<Account> pageResult = new PageResult(page.getNumber()+1, page.getSize(), page.getTotalElements(), page.getContent());
        Result<Account> result = new Result(HttpStatus.OK, "获取员工信息成功！", pageResult);
        return result;
    }

    /***************修改员工模块*************/
    @ApiOperation(value = "修改")
    @PutMapping("/update")
    public Result<Account> update(@RequestBody Account account) {
        accountService.update(account);
        return new Result(HttpStatus.OK, "更新成功！", null);
    }

}
