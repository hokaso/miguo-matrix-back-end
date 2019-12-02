package com.tocean.k12.test;

import com.miguo.matrix.Application;
import com.miguo.matrix.repository.staff.AccountRepository;
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

    @Test
    public void test() throws IOException {
accountRepository.deleteById("1",new Date());
    }

}