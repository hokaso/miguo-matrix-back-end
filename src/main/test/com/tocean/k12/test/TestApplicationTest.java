package com.tocean.k12.test;

import com.miguo.matrix.Application;
import com.miguo.matrix.entity.staff.Account;
import com.miguo.matrix.repository.staff.AccountRepository;
import com.miguo.matrix.service.staff.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestApplicationTest {


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    public void test() throws IOException {
//        System.out.println( accountService.findAll(0,5).getContent());
//       Account a= accountRepository.findByNickname("Hocassian");
//        System.out.println(a);
    Account account = new Account();
    account.setNickname("test");
    account.setPassword("222");
//    accountService.update(account);

    }

}