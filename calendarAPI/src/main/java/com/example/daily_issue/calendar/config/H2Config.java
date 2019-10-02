package com.example.daily_issue.calendar.config;

import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class H2Config {

    @Bean
    @Profile("local")
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() throws SQLException
    {
        Server.createTcpServer(
                "-tcp",
                "-tcpAllowOthers",
                //"-ifNotExists",
                "-tcpPort", "9092").start();

        return new HikariDataSource();

    }

}
