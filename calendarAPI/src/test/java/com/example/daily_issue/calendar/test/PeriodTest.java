package com.example.daily_issue.calendar.test;

import com.example.daily_issue.calendar.domain.RepeatableTask;
import com.example.daily_issue.calendar.service.RepeatableTaskService;
import com.example.daily_issue.calendar.service.util.CalendarCalculator;
import com.example.daily_issue.calendar.vo.DateRange;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@SpringBootTest
public class PeriodTest {

    @Autowired
    CalendarCalculator calculator;

    @Autowired
    RepeatableTaskService repeatableTaskService;

    @Test
    public void repeatWeekTest()
    {
        RepeatableTask task = new RepeatableTask();
        task.setRepeatStartDate(LocalDate.of(2019, 11, 5));
        task.setRepeatEndDate(LocalDate.of(2019, 11, 28));

        // 최초 일정 설정 시 2019. 10. 22 ~ 2019. 10. 23 2일동안 발생으로 설정함
        task.getBasicTask().setTaskStartDate(LocalDate.of(2019, 10, 22));
        task.getBasicTask().setTaskEndDate(LocalDate.of(2019,10,23));

        // 반복 주기는 2일마다
//        task.setRepeatChronoUnit(ChronoUnit.DAYS);
//        task.setRepeatAmount(2);
        task.setRepeatDayOfWeeks(DayOfWeek.TUESDAY);
        task.setRepeatAmount(1);


        // 화면 출력 기준일은 2019. 11월 달력
        LocalDate baseDate = LocalDate.of(2019, 11, 1);
        DateRange displayDateRange = calculator.getDisplayDateRange(ChronoUnit.MONTHS, baseDate);
        DateRange taskableDateRange = calculator.getTaskableDateRange(displayDateRange, task);


        Set<LocalDate> result = repeatableTaskService.listRepeatedTaskByDayOfWeeks(taskableDateRange, task);

        result.forEach(System.out::println);
    }



}
