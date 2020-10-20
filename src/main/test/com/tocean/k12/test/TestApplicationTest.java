package com.tocean.k12.test;

import com.miguo.matrix.Application;
import com.miguo.matrix.entity.client.Article;
import com.miguo.matrix.entity.client.Video;
import com.miguo.matrix.repository.client.ArticleRepository;
import com.miguo.matrix.repository.staff.AccountRepository;
import com.miguo.matrix.service.client.ArticleService;
import com.miguo.matrix.service.client.VideoService;
import com.miguo.matrix.service.staff.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestApplicationTest {


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository ArticleRepository;

    @Autowired
    private VideoService videoService;
    @Value("${file.path}")
    private String filePath;
    @Test
    public void test() throws IOException {

        System.out.println(System.getProperty("os.name"));

    }

}