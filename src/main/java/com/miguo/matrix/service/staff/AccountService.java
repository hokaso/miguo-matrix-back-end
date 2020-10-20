package com.miguo.matrix.service.staff;

import com.miguo.matrix.entity.staff.Account;
import com.miguo.matrix.repository.staff.AccountRepository;
import com.miguo.matrix.utils.SnowflakeIdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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

    @Autowired
    private HttpSession session;

    /**
     * 查找未被删除的
     * @param page
     * @param size
     * @return
     */
    public Page<Account> findAllExist(int page, int size) {
        page--;
        Pageable pageable = PageRequest.of(page, size);
        return accountRepository.findAllExistAccount(pageable);
    }

    /**
     * 查找已被软删除的
     * @param page
     * @param size
     * @return
     */
    public Page<Account> findAllDeleted(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        return accountRepository.findAllDeletedAccount(pageable);
    }

    /**
     * 分页返回标题或内容包含某关键字的视频条目（员工使用），当关键字为空时按更新时间顺序返回所有
     * @param keywords
     * @param page
     * @param size
     * @param direction
     * @return
     */
    public Page<Account> findAllByKeywords(String keywords, String active, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "update_at");
        Boolean activeTemp = Boolean.parseBoolean(active);
        return accountRepository.findAccountByKeywords(keywords, activeTemp, pageable);
    }

    /**
     * 通过id查找某一个用户
     * @param id
     * @return
     */
    public Account findOneById(String id) {
        return accountRepository.findNicknameById(id);
    }

    /**
     * 通过nickname查找某一个用户
     * @param nickname
     * @return
     */

    public Account findOne(String nickname) {
        return accountRepository.findByNickname(nickname);
    }

    /**
     * 添加用户
     * @param account
     */
    public void add(Account account) {
        account.setCreateAt(new Date());
        account.setUpdateAt(new Date());
        account.setPassword("123456");
        account.setId(snowflakeIdWorker.nextId());
        account.setCreateBy((String) session.getAttribute("user"));
        account.setUpdateBy((String) session.getAttribute("user"));
        account.setIsDel(false);
        accountRepository.save(account);
    }

    /**
     * 软删除或恢复用户
     * @param ids
     */
    public void modify(String ids, String active) {
        String[] deleteNicknames = ids.split(",");
        Boolean activeTemp = Boolean.parseBoolean(active);
        for (String deleteNickname : deleteNicknames) {
            accountRepository.deleteByNickname(activeTemp, deleteNickname, new Date(), (String) session.getAttribute("user"));
        }
    }

    /**
     * 更新用户
     * @param account
     */
    public void update(Account account) {
        Account accountTemp = this.findOneById(account.getId());
        BeanUtils.copyProperties(account, accountTemp);
        accountTemp.setUpdateAt(new Date());
        accountTemp.setUpdateBy((String) session.getAttribute("user"));
        accountRepository.saveAndFlush(accountTemp);
    }

    /**
     * 判断用户是否存在，用户不存在，直接返回，用户存在，进入下一步密码判断
     * @param nickname
     * @return
     */
    public boolean isExistByNickname(String nickname) {
        Account account = accountRepository.findByNickname(nickname);
        if(account != null){
            return !account.getIsDel();
        }
        return false;
    }

}
