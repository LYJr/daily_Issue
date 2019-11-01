package com.example.daily_issue.login.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;

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