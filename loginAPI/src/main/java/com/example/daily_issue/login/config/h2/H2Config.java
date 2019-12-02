package com.example.daily_issue.login.config.h2;

import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.SQLException;

@Profile("local-h2")
@Configuration
public class H2Config {

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() throws SQLException
    {
        Server.createTcpServer(
                "-tcp",
                "-tcpAllowOthers",
                "-tcpPort",
                "9092"
        ).start();

        return new HikariDataSource();

    }

}