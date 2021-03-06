package com.miguo.matrix.repository.client;

import com.miguo.matrix.entity.client.Article;
import com.miguo.matrix.vo.web.ArticleVo;
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
     * ※客户方法
     *
     * 分页返回标题或内容包含某关键字且未被下架的文章条目
     * @param keywords
     * @param pageable
     * @return
     */
    @Query(value = "select * from client_article WHERE ( article LIKE %:#{#keywords}% OR title LIKE %:#{#keywords}% ) and is_del = false and status = 'reviewed' order by create_at desc",nativeQuery = true)
    Page<Article> findArticleByKeywords(String keywords, Pageable pageable);

    /**
     * ※客户方法
     *
     * 返回挂上首页的文章条目
     * @param pageable
     * @param keywords
     * @return
     */
    @Query("SELECT new com.miguo.matrix.vo.web.ArticleVo(" +
            "a.id, " +
            "SUBSTRING ( a.article, 1, 70 ) as article, " +
            "a.title, " +
            "a.pic, " +
            "a.author, " +
            "a.createAt) " +
            "from Article a " +
            "WHERE ( a.article LIKE %:#{#keywords}% OR a.title LIKE %:#{#keywords}% ) " +
            "and a.isDel = false " +
            "and a.status = 'reviewed' " +
            "order by a.createAt desc")
    Page<ArticleVo> findSomeArticle(String keywords, Pageable pageable);

    /**
     * ※员工方法：进入某一篇文章修改时使用
     *
     * 通过id找文章
     * @param id
     * @return
     */
    Article findArticleById(String id);

    /**
     * ※员工方法
     *
     * 分页返回标题或内容包含某关键字的文章条目，当关键字为空时按更新时间顺序返回所有
     * @param keywords
     * @param pageable
     * @return
     */
    @Query(value = "select * from client_article WHERE article LIKE %:#{#keywords}% OR title LIKE %:#{#keywords}%", nativeQuery = true)
    Page<Article> staffFindArticleByKeywords(String keywords, Pageable pageable);

    /**
     * ※审核方法：该方法与网络安全有关，与业务需求无关，未来迭代版本中使用
     *
     * 通过把「is_del」改为「true」来下架多篇文章（并非删除）
     * @param id
     * @param date
     * @param updateBy
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update client_article set is_del = true, update_at = :#{#date}, update_by = :#{#updateBy} where id = :#{#id}", nativeQuery = true)
    void deleteSomeById(String id, Date date, String updateBy);

    /**
     * ※审核方法
     *
     * 分页按条件分类查找删除与否的文章
     * @param pageable
     * @param active
     * @param keywords
     * @return
     */
    @Query(value = "select * from client_article where ( article LIKE %:#{#keywords}% OR title LIKE %:#{#keywords}% ) and is_del = :active and status != 'draft'", nativeQuery = true)
    Page<Article> findAllClassArticle(String keywords, Pageable pageable, Boolean active);

    /**
     * ※审核方法
     *
     * 分页返回所有的文章条目
     * @param pageable
     * @param keywords
     * @return
     */
    @Query(value = "select * from client_article where ( article LIKE %:#{#keywords}% OR title LIKE %:#{#keywords}% ) and status != 'draft'", nativeQuery = true)
    Page<Article> findAllExistArticle(String keywords, Pageable pageable);
}
