package com.miguo.matrix.repository.client;

import com.miguo.matrix.entity.client.Article;
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
     * ※审核方法
     *
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
     * ※客户方法
     *
     * 分页返回标题或内容包含某关键字且未被下架的文章条目
     * @param keywords
     * @param pageable
     * @return
     */
    @Query(value = "select * from client_article WHERE article LIKE %:#{#keywords}% OR title LIKE %:#{#keywords}% and is_del = false",nativeQuery = true)
    Page<Article> findArticleByKeywords(String keywords,Pageable pageable);

    /**
     * ※员工方法
     *
     * 分页返回标题或内容包含某关键字的文章条目，当关键字为空时按更新时间顺序返回所有
     * @param keywords
     * @param pageable
     * @return
     */
    @Query(value = "select * from client_article WHERE article LIKE %:#{#keywords}% OR title LIKE %:#{#keywords}%",nativeQuery = true)
    Page<Article> staffFindArticleByKeywords(String keywords, Pageable pageable);

    /**
     * ※审核方法
     *
     * 分页按条件分类查找删除与否的文章
     * @param pageable
     * @param active
     * @return
     */
    @Query(value = "select * from client_article where is_del = :active and status != 'draft'",nativeQuery = true)
    Page<Article> findAllDeletedArticle(Pageable pageable, Boolean active);

    /**
     * ※审核方法
     *
     * 分页返回所有的文章条目
     * @param pageable
     * @return
     */
    @Query(value = "select * from client_article where status != 'draft'",nativeQuery = true)
    Page<Article> findAllExistArticle(Pageable pageable);

    /**
     * 通过id找文章
     * @param id
     * @return
     */
    Article findArticleById(String id);
}
