package com.miguo.matrix.repository;

import com.miguo.matrix.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Hocassian
 */
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
}
