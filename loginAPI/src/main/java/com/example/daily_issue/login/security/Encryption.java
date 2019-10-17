package com.example.daily_issue.login.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class Encryption {

    /**
     * 다른 암호화 사용시 encoders에 추가할 것.
     * @return
     */
    @Bean
    public static PasswordEncoder delegatingPasswordEncoder(){
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());

        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

}
