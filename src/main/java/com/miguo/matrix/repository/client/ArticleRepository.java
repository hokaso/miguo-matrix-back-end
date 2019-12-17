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

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {

    // 软删除某一篇文章
    @Modifying
    @Transactional
    @Query(value = "update client_article set is_del = true , update_at = :#{#date},update_by = :#{#updateBy} where id = :#{#id} ", nativeQuery = true)
    void deleteById(String id, Date date,String updateBy);

    // 分页查询文章内容或标题包含该关键字且未被软删除的文章
    @Query(value = "select * from client_article WHERE article LIKE %:#{#keywords}% OR title LIKE %:#{#keywords}% and is_del = false",nativeQuery = true)
    Page<Article> findArticleByKeywords(String keywords,Pageable pageable);

    // 查找所有已被软删除的文章
    @Query(value = "select * from client_article where is_del = true",nativeQuery = true)
    Page<Article> findAllDeletedArticle(Pageable pageable);

    // 查找所有未被软删除的文章
    @Query(value = "select * from client_article where is_del = false",nativeQuery = true)
    Page<Article> findAllExistArticle(Pageable pageable);

    // 通过id找文章
    Article findArticleById(String id);
}
