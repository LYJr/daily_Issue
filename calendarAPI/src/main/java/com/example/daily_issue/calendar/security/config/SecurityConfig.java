package com.example.daily_issue.calendar.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.cors().disable();

        http.formLogin()
                .and().httpBasic();

        http.authorizeRequests()
                .mvcMatchers("/calendar").permitAll()
                .mvcMatchers("/calendar/**").authenticated();

        http.mvcMatcher("/**").authorizeRequests()
                .anyRequest().permitAll();
    }


}
