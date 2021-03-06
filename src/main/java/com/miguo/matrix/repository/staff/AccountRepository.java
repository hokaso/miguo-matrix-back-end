package com.miguo.matrix.repository.staff;

import com.miguo.matrix.entity.staff.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Noah
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    /**
     * 通过id(账号)查找某一个用户，忽略是否被软删除过
     * @param id
     * @return
     */
    Account findNicknameById(String id);

    /**
     * 通过nickname(账号)查找某一个用户，忽略是否被软删除过
     * @param nickname
     * @return
     */
    Account findByNickname(String nickname);

    /**
     * 软删除某一个用户
     * @param id
     * @param date
     * @param updateBy
     * @param active
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update com_staff set is_del = :#{#active} , update_at = :#{#date} , update_by = :#{#updateBy} where id = :#{#id} ", nativeQuery = true)
    void deleteByNickname(Boolean active, String id, Date date, String updateBy);

    /**
     * 查询所有未被软删除的用户
     * @param pageable
     * @return
     */
    @Query(value = "select * from com_staff where is_del = false",nativeQuery = true)
    Page<Account> findAllExistAccount(Pageable pageable);

    /**
     * 查询所有已被软删除的用户
     * @param pageable
     * @return
     */
    @Query(value="select * from com_staff where is_del = true",nativeQuery = true)
    Page<Account> findAllDeletedAccount(Pageable pageable);

    /**
     * 根据关键词、存在状态查询
     * @param keywords
     * @param active
     * @param pageable
     * @return
     */
    @Query(value = "select * from com_staff WHERE ( name LIKE %:#{#keywords}% OR nickname LIKE %:#{#keywords}% ) AND is_del = :active ", nativeQuery = true)
    Page<Account> findAccountByKeywords(String keywords, Boolean active, Pageable pageable);
}
