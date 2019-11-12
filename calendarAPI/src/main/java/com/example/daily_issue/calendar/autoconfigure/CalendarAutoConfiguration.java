package com.example.daily_issue.calendar.autoconfigure;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-07
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-07)
 */

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 */
@Configuration(proxyBeanMethods = false)
/*@PropertySources(value = {
        @PropertySource(value = "classpath:config/module/application-calendar.properties"),
        @PropertySource(value = "classpath:config/module/application-calendar-${spring.profiles.active}.properties", ignoreResourceNotFound = true)
})*/
@EnableConfigurationProperties(CalendarProperties.class)
public class CalendarAutoConfiguration {
}
