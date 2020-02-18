package com.miguo.matrix.controller;

import com.miguo.matrix.dto.PageResult;
import com.miguo.matrix.dto.Result;
import com.miguo.matrix.dto.AdminSearchDto;
import com.miguo.matrix.dto.staff.UpdatePasswordDto;
import com.miguo.matrix.entity.client.Article;
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

    @ApiOperation(value = "分页查询所有未被删除的员工")
    @GetMapping("/fina_all_exist/{page}/{size}")
    public Result<PageResult<Account>> findAllExist(@PathVariable("page") int page, @PathVariable("size") int size) {
        Result<PageResult<Account>> result = new Result<>();
        try {
            Page<Account> accountPage = accountService.findAllExist(page, size);
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

    @ApiOperation("分页查找所有模糊查询的员工，接收按时间正逆排序、是否下架")
    @PostMapping("/find_all_by_keywords")
    public Result<PageResult<Account>> findAll(@RequestBody AdminSearchDto searchDto) {
        Result<PageResult<Account>> result = new Result<>();
        Page<Account> page;
        try {
            if (searchDto.getKeywords() == null || "".equals(searchDto.getKeywords())) {
                page = accountService.findAllByKeywords("", searchDto.getActive(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
                // 当关键字为空时，查询所有
            } else {
                page = accountService.findAllByKeywords(searchDto.getKeywords(), searchDto.getActive(), searchDto.getPage(), searchDto.getSize(), searchDto.getDirection());
            }
            PageResult<Account> pageResult = new PageResult<>();
            pageResult.setSize(searchDto.getSize()).setPage(searchDto.getPage()).setData(page.getContent()).setTotal(page.getTotalElements());
            result.setMessage("success").setCode(HttpStatus.OK).setData(pageResult);
        } catch (Exception e) {
            result.setMessage("fail").setCode(HttpStatus.OK).setData(null);
        }

        return result;
    }

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

    /**
     * 批量下架
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量软删除员工")
    @GetMapping("/modify/{ids}/{active}")
    public Result<String> delete(@PathVariable("ids") String ids, @PathVariable("active") String active) {
        Result<String> result = new Result<>();
        result.setCode(HttpStatus.OK).setMessage("delete");
        try {
            accountService.modify(ids,active);
            result.setData("success");
        } catch (Exception e) {
            result.setData("fail");
        }
        return result;
    }

    @ApiOperation("分页按条件分类查找删除与否的文章")
    @GetMapping("/article/find_all_deleted/{page}/{size}/{active}")
    public Result<PageResult<Article>> articleFindAllDeleted(@PathVariable("page") int page, @PathVariable("size") int size, @PathVariable("active") String active) {
        Result<PageResult<Article>> result = new Result<>();
        Page<Article> pageTemp;
        try {
            pageTemp = articleService.findAllDeleted(page, size, active);
            PageResult<Article> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

    @ApiOperation("分页查找所有的文章")
    @GetMapping("/article/find_all_exist/{page}/{size}")
    public Result<PageResult<Article>> articleFindAllExist(@PathVariable("page") int page, @PathVariable("size") int size) {
        Result<PageResult<Article>> result = new Result<>();
        Page<Article> pageTemp;
        try {
            pageTemp = articleService.findAllExist(page, size);
            PageResult<Article> pageResult = new PageResult<>();
            pageResult.setTotal(pageTemp.getTotalElements()).setData(pageTemp.getContent()).setPage(page).setSize(size);
            result.setData(pageResult).setCode(HttpStatus.OK).setMessage("success");
        } catch (Exception e) {
            result.setData(null).setCode(HttpStatus.OK).setMessage("fail");
        }
        return result;
    }

}
