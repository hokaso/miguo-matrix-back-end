package com.miguo.matrix.repository.client;

import com.miguo.matrix.entity.client.Article;
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
 * @author Hocassian
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {

    /**
     * 通过把「is_del」改为「true」来下架多篇文章（并非删除）
     * @param id
     * @param date
     * @param updateBy
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update client_article set is_del = true , update_at = :#{#date},update_by = :#{#updateBy} where id = :#{#id} ", nativeQuery = true)
    void deleteSomeById(String id, Date date,String updateBy);

    /**
     * 分页返回标题或内容包含某关键字且未被下架的文章条目（客户使用）
     * @param keywords
     * @param pageable
     * @return
     */
    @Query(value = "select * from client_article WHERE article LIKE %:#{#keywords}% OR title LIKE %:#{#keywords}% and is_del = false",nativeQuery = true)
    Page<Article> findArticleByKeywords(String keywords,Pageable pageable);

    /**
     * 分页返回标题或内容包含某关键字的文章条目（员工使用），当关键字为空时按更新时间顺序返回所有
     * @param keywords
     * @param pageable
     * @return
     */
    @Query(value = "select * from client_article WHERE article LIKE %:#{#keywords}% OR title LIKE %:#{#keywords}%",nativeQuery = true)
    Page<Article> staffFindArticleByKeywords(String keywords,Pageable pageable);

    /**
     * 分页返回所有未被下架的文章条目
     * @param pageable
     * @return
     */
    @Query(value = "select * from client_article where is_del = true",nativeQuery = true)
    Page<Article> findAllDeletedArticle(Pageable pageable);

    /**
     * 分页返回所有已被下架的文章条目
     * @param pageable
     * @return
     */
    @Query(value = "select * from client_article where is_del = false",nativeQuery = true)
    Page<Article> findAllExistArticle(Pageable pageable);

    /**
     * 通过id找文章
     * @param id
     * @return
     */
    Article findArticleById(String id);
}
