package com.miguo.matrix.service.client;

import com.miguo.matrix.entity.client.Article;
import com.miguo.matrix.repository.client.ArticleRepository;
import com.miguo.matrix.utils.SnowflakeIdWorker;
import com.miguo.matrix.vo.web.ArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author Hocassian
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private HttpSession session;

    public ArticleService() {
    }

    /**
     * ※员工方法
     *
     * 添加文章
     * @param article
     * @return
     */
    public String add(Article article){
        article.setCreateAt(new Date());
        article.setUpdateAt(new Date());
        article.setId(snowflakeIdWorker.nextId());
        article.setIsDel(false);
        article.setCreateBy((String) session.getAttribute("user"));
        article.setUpdateBy((String) session.getAttribute("user"));
        articleRepository.save(article);
        return article.getId();
    }

    /**
     * ※审核方法
     *
     * 批量下架文章
     * @param ids
     */
    public void delete(String ids){
        String[] deleteIds = ids.split(",");
        for (String deleteId : deleteIds) {
            articleRepository.deleteSomeById(deleteId, new Date(), (String) session.getAttribute("user"));
        }
    }

    /**
     * ※员工方法
     *
     * 删除一篇文章
     * @param id
     */
    public void deleteOne(String id){
        articleRepository.deleteById(id);
    }

    /**
     * ※客户方法
     *
     * 返回挂上首页的文章条目
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    public Page<ArticleVo> findSome(String keywords, int page, int size){
        page--;
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findSomeArticle(keywords, pageable);
    }

    /**
     * ※客户方法
     *
     * 分页返回标题或内容包含某关键字且未被下架的文章条目
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findAllByKeywords(String keywords,int page,int size){
        page--;
        Pageable pageable = PageRequest.of(page, size);
        return articleRepository.findArticleByKeywords(keywords,pageable);
    }

    /**
     * ※员工方法
     *
     * 分页返回标题或内容包含某关键字的文章条目，当关键字为空时按更新时间顺序返回所有
     * @param keywords
     * @param page
     * @param size
     * @param direction
     * @return
     */
    public Page<Article> staffFindAllByKeywords(String keywords, int page, int size, Sort.Direction direction){
        page--;
        Pageable pageable = PageRequest.of(page, size, direction, "update_at");
        return articleRepository.staffFindArticleByKeywords(keywords, pageable);
    }

    /**
     * ※审核方法
     *
     * 分页分类返回所有文章条目
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findAllClass(String keywords, int page,int size, String active, Sort.Direction direction){
        page--;
        Pageable pageable=PageRequest.of(page,size, direction, "create_at");
        Boolean activeTemp = Boolean.parseBoolean(active);
        return articleRepository.findAllClassArticle(keywords, pageable, activeTemp);
    }

    /**
     * ※审核方法
     *
     * 分页不分类返回所有文章条目
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findAllExist(String keywords, int page,int size, Sort.Direction direction){
        page--;
        Pageable pageable=PageRequest.of(page,size, direction, "create_at");
        return articleRepository.findAllExistArticle(keywords, pageable);
    }

    /**
     * 通过id找文章
     * @param id
     * @return
     */
    public Article findOneById(String id)
    {
        return articleRepository.findArticleById(id);
    }

    /**
     * 更新文章
     * @param article
     */
    public void update(Article article){
        Article articleTemp=this.findOneById(article.getId());
        BeanUtils.copyProperties(article, articleTemp);
        articleTemp.setUpdateBy((String) session.getAttribute("user"));
        articleTemp.setUpdateAt(new Date());
        articleRepository.saveAndFlush(articleTemp);
    }
}
