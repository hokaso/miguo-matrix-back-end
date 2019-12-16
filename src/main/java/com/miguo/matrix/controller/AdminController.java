package com.miguo.matrix.controller;

import com.miguo.matrix.dto.PageResult;
import com.miguo.matrix.dto.Result;
import com.miguo.matrix.dto.staff.UpdatePasswordDto;
import com.miguo.matrix.entity.staff.Account;
import com.miguo.matrix.service.client.ArticleService;
import com.miguo.matrix.service.client.VideoService;
import com.miguo.matrix.service.staff.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Noah
 */
@Api("管理员的接口，该接口管理员工的增删查改")
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private VideoService videoService;

    // 分页查询所有员工
    @ApiOperation(value = "分页查询所有员工")
    @GetMapping("/find_all/{page}/{size}")
    public Result<PageResult<Account>> findAll(@PathVariable("page") int page, @PathVariable("size") int size) {
        Result<PageResult<Account>> result = new Result<>();
        try {
            Page<Account> accountPage = accountService.findAll(page, size);
            // 页数减1已经在service层减了
            PageResult<Account> pageResult = new PageResult<>();
            pageResult.setData(accountPage.getContent());
            pageResult.setPage(page).setSize(size).setTotal(accountPage.getTotalElements());
            result.setData(pageResult).setMessage("success").setCode(HttpStatus.OK);
        } catch (Exception e) {
            result.setData(null).setMessage("fail").setCode(HttpStatus.OK);
        }

        return result;
    }

    // 分页查询所有被删除的员工
    @ApiOperation(value = "分页查询所有被删除的员工")
    @GetMapping("/fina_all_deleted/{page}/{size}")
    public Result<PageResult<Account>> findAllDeleted(@PathVariable("page") int page, @PathVariable("size") int size) {
        Result<PageResult<Account>> result = new Result<>();
        try {
            Page<Account> accountPage = accountService.findAllDeleted(page, size);
            // 页数减1已经在service层减了
            PageResult<Account> pageResult = new PageResult<>();
            pageResult.setData(accountPage.getContent());
            pageResult.setPage(page).setSize(size).setTotal(accountPage.getTotalElements());
            result.setData(pageResult).setMessage("success").setCode(HttpStatus.OK);
        } catch (Exception e) {
            result.setData(null).setMessage("fail").setCode(HttpStatus.OK);
        }

        return result;
    }

    // 找某一个员工的信息
    @ApiOperation(value = "通过nickname找员工信息")
    @GetMapping("/find_one_by_nickname/{nickname}")
    public Result<Account> findOneByNickname(@PathVariable("nickname") String nickname) {
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
        try {
            accountService.add(account);
            result.setMessage("add").setData("success").setCode(HttpStatus.OK);

        } catch (Exception e) {
            result.setMessage("add").setData("fail").setCode(HttpStatus.OK);
        }
        return result;
    }



    @ApiOperation(value = "修改员工权限")
    @PutMapping("/update_level")
    public Result<String> updateLevel(@RequestBody String level) {
        Result<String> result = new Result<>();
        try {
            Account account = accountService.findOne("test"); // 写死，到时候用session代替
            account.setLevel(level);
            accountService.updateLevel(account);
            result.setData("success");
        } catch (Exception e) {
            result.setData("fail");
        }
        return result;
    }

    // 带body
    @ApiOperation(value = "批量软删除员工")
    @DeleteMapping("/delete/{nicknames}")
    public Result<String> delete(@PathVariable String nicknames) {
        Result<String> result = new Result<>();
        result.setCode(HttpStatus.OK).setMessage("delete");
        try {
            accountService.delete(nicknames);
            result.setData("success");
        } catch (Exception e) {
            result.setData("fail");
        }
        return result;
    }


}
