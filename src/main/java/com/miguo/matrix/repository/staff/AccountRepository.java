package com.miguo.matrix.repository.staff;

import com.miguo.matrix.entity.staff.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Hocassian
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    /**
     * 功能描述：模糊查询
     *
     * @param nickname
     * @return Account
     */
    Account findByNickname(String nickname);

    /**
     * 功能描述：软删除某一个用户
     *
     * @param id updateAt
     * @return void
     */
    @Modifying
    @Transactional
    @Query(value = "update com_staff set is_del = true , update_At = :#{#updateAt} where id = :#{#id}",nativeQuery = true)
    void deleteById(String id,Date updateAt);
}
