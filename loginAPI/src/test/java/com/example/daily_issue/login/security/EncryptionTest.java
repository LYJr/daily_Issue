package com.example.daily_issue.login.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SecurityTestExecutionListeners
public class EncryptionTest {

    private PasswordEncoder passwordEncoder;

    @Test
    public void contectLoads(){}


    @Test
    public void delegatingPasswordEncoder() {

        String name = "name";

        String test = passwordEncoder.encode(name);

        System.out.println(test);


    }

    @Test
    public void test(){

    }

}