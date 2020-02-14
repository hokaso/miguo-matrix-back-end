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
import java.util.List;

/**
 * @author Noah
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    /**
     * 通过nickname(账号)查找某一个用户，忽略是否被软删除过
     * @param nickname
     * @return
     */
    Account findByNickname(String nickname);

    /**
     * 软删除某一个用户
     * @param nickname
     * @param date
     * @param updateBy
     */
    @Modifying
    @Transactional
    @Query(value = "update com_staff set is_del = true , update_at = :#{#date} , update_by = :#{#updateBy} where  nickname = :#{#nickname} ", nativeQuery = true)
    void deleteByNickname(String nickname, Date date,String updateBy);

    // 查询所有未被软删除的用户
    @Query(value = "select * from com_staff where is_del = false",nativeQuery = true)
    Page<Account> findAllAccount(Pageable pageable);

    // 查询所有已被软删除的用户
    @Query(value="select * from com_staff where is_del = true",nativeQuery = true)
    Page<Account> findAllDeletedAccount(Pageable pageable);

}
