package com.example.daily_issue.calendar.config.application;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class ApplicationConfig {

    @Bean
    public ModelMapper getModelMapper()
    {
        return new ModelMapper();
    }


    // not global
    /*@Bean
    public ObjectMapper getObjectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();;
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }*/
}
