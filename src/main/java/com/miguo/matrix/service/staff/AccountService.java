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
import java.util.List;

/**
 * @author Noah
 * @功能 对工作人员的管理，增删查改等功能
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    // 根据分页查找所有
    public Page<Account> findAll(int page, int size) {
        page--;
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> account = accountRepository.findAllAccount(pageable);
        return account;
    }

    // 查找所有已被软删除的
    public Page<Account> findAllDeleted(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        Page<Account> account =accountRepository.findAllDeletedAccount(pageable);
        return account;
    }

    // 同过nickname查找某一个用户
    public Account findOne(String nickname) {
        return accountRepository.findByNickname(nickname);
    }

    // 添加用户
    public void add(Account account) {
        account.setCreateAt(new Date());
        account.setUpdateAt(new Date());
        account.setId(snowflakeIdWorker.nextId());
        account.setCreateBy("admin");
        account.setUpdateBy("admin");
        account.setIsDel(false);
        accountRepository.save(account);
    }

    // 软删除用户
    public void delete(String nicknames) {
        String deleteNicknames[] = nicknames.split(",");
        for(int i =0;i<deleteNicknames.length;i++){
            accountRepository.deleteByNickname(deleteNicknames[i], new Date(),"admin");
        }
    }


    // 更新用户，只能更新名
    public void updateName(Account account) {
        Account accountTemp = this.findOne(account.getNickname());
        accountTemp.setUpdateAt(new Date());
        accountTemp.setUpdateBy("admin");
        accountTemp.setName(account.getName());
        accountRepository.saveAndFlush(accountTemp);
    }

    // 更新用户，只更新权限
    public void updateLevel(Account account) {
        Account accountTemp = this.findOne(account.getNickname());
        accountTemp.setUpdateAt(new Date());
        accountTemp.setUpdateBy("admin");
        accountTemp.setLevel(account.getLevel());
        accountRepository.saveAndFlush(accountTemp);
    }

    // 更新用户，只更新密码
    public void updatePaswword(Account account) {
        Account accountTemp = this.findOne(account.getNickname());
        accountTemp.setUpdateAt(new Date());
        accountTemp.setUpdateBy("test"); // 写死，用session代替
        accountTemp.setPassword(account.getPassword());
        accountRepository.saveAndFlush(accountTemp);
    }

    // 判断用户是否存在
    public boolean isExistByNickname(String nickname) {
        Account account = accountRepository.findByNickname(nickname);
        if (account == null) {
            return false;
            // 用户不存在，直接返回
        } else {
            return true;
            // 用户存在，进入下一步密码判断
        }
    }

}
