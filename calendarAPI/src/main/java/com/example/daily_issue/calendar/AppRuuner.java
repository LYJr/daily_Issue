package com.example.daily_issue.calendar;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-07
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-07)
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 *
 *
 */
@Component
public class AppRuuner implements ApplicationRunner {

    @Autowired
    AuditorAware auditorAware;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(auditorAware);

    }
}
