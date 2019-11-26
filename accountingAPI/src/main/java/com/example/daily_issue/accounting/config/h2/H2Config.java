package com.example.daily_issue.accounting.config.h2;

import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 로컬 테스트용 H2 설정 파일
 * @author 진환
 */
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
                "-tcpPort",
                "9092"
        ).start();

        return new HikariDataSource();

    }

}

