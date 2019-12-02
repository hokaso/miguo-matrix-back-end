package com.miguo.matrix.repository.client;

import com.miguo.matrix.entity.client.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientArticleRepository extends JpaRepository<Article, String> {

}
