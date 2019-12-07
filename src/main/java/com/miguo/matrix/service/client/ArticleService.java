package com.miguo.matrix.service.client;

import com.miguo.matrix.entity.client.Article;
import com.miguo.matrix.repository.client.ArticleRepository;
import com.miguo.matrix.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    // 添加文章
    public void add(Article article){
        article.setCreateAt(new Date());
        article.setUpdateAt(new Date());
        article.setId(snowflakeIdWorker.nextId());
        article.setIsDel(false);
        article.setCreateBy("test"); // 写死，到时候用session代替
        article.setUpdateBy("test"); // 写死，到时候用session代替
        articleRepository.save(article);
    }

    // 批量软删除文章
    public void delete(String ids){
        String deleteIds[] = ids.split(",");
        for(int i =0;i<deleteIds.length;i++){
            articleRepository.deleteById(deleteIds[i],new Date(),"test");  // 写死，用session代替
        }

    }

    // 查询 标题或者文章内容 包含 该关键字的所有文章
    public Page<Article> findAllByKeywords(String keywords,int page,int size){
        page--;
        Pageable pageable = PageRequest.of(page, size);
        Page<Article> page1 = articleRepository.findArticleByKeywords(keywords,pageable);
        return page1;
    }


    // 查找所有已被删除的文章
    public Page<Article> findAllDeleted(int page,int size){
        page--;
        Pageable pageable=PageRequest.of(page,size);
        Page<Article> page1 = articleRepository.findAllDeletedArticle(pageable);
        return page1;
    }

    // 通过id查找该文章
    public Article findOneById(String id)
    {
        return articleRepository.findArticleById(id);
    }

    // 更新文章
    public void update(Article article){
        Article article1=articleRepository.findArticleById(article.getId());
        article1.setTitle(article.getTitle());
        article1.setArticle(article.getArticle());
        article1.setUpdateBy("test"); // 写死，session代替
        article1.setUpdateAt(new Date());
        articleRepository.saveAndFlush(article1);
    }


}
