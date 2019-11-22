package com.example.daily_issue.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.cors().disable();

        http.authorizeRequests()
                .mvcMatchers("/**", "/member/join", "/member/login").permitAll()
                .anyRequest().authenticated();

        http.formLogin();
        http.logout();
    }

    @Bean
    public PasswordEncoder passwordEncoder (){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
