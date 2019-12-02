package com.miguo.matrix.repository;

import com.miguo.matrix.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Hocassian
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    //    @Query(value = "select * from com_staff where nickname LIKE %?%" ,nativeQuery = true)
    //    public List<Account> findByNameLike(String name);
    /**
     * 功能描述：模糊查询
     *
     * @param nickname
     * @return
     */
    Account findByNickname(String nickname);

    // 软删除某一个用户
    @Modifying
    @Transactional
    @Query(value = "update com_staff set is_del = true , update_At = :#{#updateAt} where id = :#{#id}",nativeQuery = true)
    void deleteById(String id,Date updateAt);
}
