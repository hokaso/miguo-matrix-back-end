package com.tocean.k12.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miguo.matrix.Application;
import com.miguo.matrix.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

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