package com.example.daily_issue.calendar.autoconfigure;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-07
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-07)
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.DayOfWeek;

/**
 *
 * calendar module에서 사용되는 property
 */
@Data
@ConfigurationProperties(prefix = "portfolio.calendar")
public class CalendarProperties {

    // week의 시작 요일
    private DayOfWeek startDayOfWeek = DayOfWeek.MONDAY;
    // week의 종료 요일
    private DayOfWeek endDayOfWeek = DayOfWeek.SUNDAY;

    // 월별 캘린더에서 한번에 표시될 week 갯수 (default : 6주 표시 / 한달 표기보다 작은 경우 compact하게 보이게 된다.)
    private int weekCountWithMonth = 6;
}
