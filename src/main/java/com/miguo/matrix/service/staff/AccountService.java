package com.miguo.matrix.service.staff;

import com.miguo.matrix.entity.staff.Account;
import com.miguo.matrix.repository.staff.AccountRepository;
import com.miguo.matrix.utils.SnowflakeIdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Hocassian
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;


    /**
     * 新增用户
     */
    public void add(Account account) {
        account.setId(Long.toString(snowflakeIdWorker.nextId()));
        account.setCreateBy("");
        account.setCreateAt(new Date());
        account.setUpdateBy("");
        account.setUpdateAt(new Date());
        accountRepository.save(account);
    }

    /**
     * 删除用户
     */
    public void del(String id) {
        accountRepository.deleteById(id);
    }

    /**
     * 分页列出所有用户
     */
    public Page<Account> select(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        return accountRepository.findAll(pageable);
    }

    /**
     * 更新用户
     */
    public void update(Account account) {
        String id = account.getId();
        Account accountTemp = accountRepository.findById(id).get();
        BeanUtils.copyProperties(account, accountTemp);
        accountTemp.setUpdateBy("");
        accountTemp.setUpdateAt(new Date());
        accountRepository.save(accountTemp);
    }

    /**
     * 注册：判断是否有该用户
     */
    public boolean exist(String nickname){
        if(accountRepository.findByNickname(nickname)== null){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 根据用户名拿密码
     */
    public String password(String nickname){
        return accountRepository.findByNickname(nickname).getPassword();
    }
}
