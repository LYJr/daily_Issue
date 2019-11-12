package com.example.daily_issue.calendar.service.util;

import com.example.daily_issue.calendar.config.autoconfigure.CalendarProperties;
import com.example.daily_issue.calendar.domain.entity.RepeatableTaskEntity;
import com.example.daily_issue.calendar.domain.vo.DateRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-07
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-07)
 */
/**
 * CalendarCalculator
 * 달력 계산에 사용되는 유용한 method를 제공하는 utility성 class
 */
@Component
public class CalendarCalculator {

    /**
     * The Calendar properties.
     * application config로 부터 가져온 property 객체
     */
    @Autowired
    CalendarProperties calendarProperties;


    /**
     * Gets taskable date range.
     * 화면에 표시될 기간과 일정 반복기간을 계산하여
     * 일정 표시가 가능한 기간을 반환
     *
     * @param displayedDateRange 화면에 표시될 기간의 시작일과 종료일이다.
     * @param repeatableTask     일정의 시작일과 종료일이다.
     *
     * @return DateRange 일정 표시가 가능한 기간
     */
    public DateRange getTaskableDateRange(DateRange displayedDateRange, RepeatableTaskEntity repeatableTask)
    {
        /*
        displayStartDate가 repeatStartDate보다 이전이면,
            startDate = repeatStartDate
        else
            startDate = displayStartDate
        */
        LocalDate startDate = displayedDateRange.getStartDate().isBefore(repeatableTask.getRepeatStartDate()) ?
                repeatableTask.getRepeatStartDate() : displayedDateRange.getStartDate();
        /*
        repeatEndDate is empty ||  displayEndDate < repeatEndDate
            endDate = displayEndDate
        else
            endDate = repeatEndDate
         */
        LocalDate endDate = (repeatableTask.getRepeatEndDate() == null) || displayedDateRange.getEndDate().isBefore(repeatableTask.getRepeatEndDate()) ?
                displayedDateRange.getEndDate() : repeatableTask.getRepeatEndDate();

        return new DateRange(startDate, endDate, displayedDateRange.getBaseDate());
    }

    /**
     * Gets display date range.
     * 지정한 일에 화면에 출력되고자 하는 유형에 따른 일자범위 (시작일/종료일)
     * 가령, '주'단위일 경우, 지정일 (targetDate)의 과거 최근 '월요일' 부터 향후 최근 '일요일'까지의 기간이다.
     * '월'단위의 경우, 지정일 (targetDate)의 '월'의 첫째날로 부터 가장 최근 '월요일' 부터 '월'의 마지막날로 부터 향후 최근 '일요일' 까지의 기간이다.
     *
     * 여기에서 'week'의 시작일과 종료일은 config에서 설정할 수 있다.
     * default는 Mon~Sun 이 'week'의 시작점과 끝점이다.
     *
     * @param type        출력하고자 하는 유형 (day/week/month)
     * @param targetDate  출력을 위한 기준일 (index date). 가령, 2019. 11. 05일이고, type이 month이면 11월 달 출력이다.
     *
     * @return DateRange 화면출력 기간
     */
    public DateRange getDisplayDateRange(ChronoUnit type, LocalDate targetDate)
    {
        DateRange dateRange;
        switch (type)
        {
            default:
            case DAYS:
                dateRange = getDayDateRange(targetDate);
                break;
            case WEEKS:
                dateRange = getWeekDateRange(targetDate);
                break;
            case MONTHS:
                dateRange = getMonthDateRange(targetDate);
                break;
        }

        return dateRange;
    }


    /**
     * Is nested day in date range boolean.
     * 주어진 기간 이내 특정 일이 포함되어있는지 여부 판단
     * 가령, 특정 일정이 화면 출력기간 범위 내에 포함되어있는가 여부를 판단할 때 사용
     *
     * @param dateRange  기간 범위
     * @param targetDate 대상 기간
     *
     * @return boolean true : 기간에 포함됨 / false : 기간에 포함되지 않음.
     */
    public boolean isNestedDayInDateRange(DateRange dateRange, LocalDate targetDate)
    {
        boolean isNested = !targetDate.isBefore(dateRange.getStartDate())
                && !targetDate.isAfter(dateRange.getEndDate());

        return isNested;
    }


    /**
     * Gets month date range.
     * 화면에 출력할 'month' 기간 산정
     * 'month'의 첫날의 이전 최근 'weekStartDay : Mon' 부터
     * 'month'의 마지막날의 이후 최근 'weekEndDay : Sun' 까지이되,
     * row의 갯수가 weekCount보다 모자를 경우
     *      odd일 경우 tail에, even일 경우에 head에 week를 append 한 결과를 출력한다.
     *
     * @param targetDate 화면출력을 위한 기준일
     *
     * @return DateRange Month 화면 출력을 위한 시작일 / 종료일
     */
    private DateRange getMonthDateRange(LocalDate targetDate)
    {

        // month의 시작일 + 이전 최근 weekStartDay
        LocalDate startDate = targetDate.with(TemporalAdjusters.firstDayOfMonth());
        startDate = startDate.with(TemporalAdjusters.previousOrSame(calendarProperties.getStartDayOfWeek()));

        // month의 종료일 + 이후 최근 weekEndDay
        LocalDate endDate = targetDate.with(TemporalAdjusters.lastDayOfMonth());
        endDate = endDate.with(TemporalAdjusters.nextOrSame(calendarProperties.getEndDayOfWeek()));


        // week row count
        int weekCount = (int) ChronoUnit.WEEKS.between(startDate, endDate) + 1;
        if(weekCount < calendarProperties.getWeekCountWithMonth() )
        {
            // 설정된 값과 row count 차이
            int toAddedWeekCount = calendarProperties.getWeekCountWithMonth()-weekCount;
            while(toAddedWeekCount > 0)
            {
                // odd이면 tail에 week 추가
                if(toAddedWeekCount%2 == 1)
                {
                    endDate = endDate.plusWeeks(1);
                }
                // even이면 head에 week 추가
                else
                {
                    startDate = startDate.minusWeeks(1);
                }
                toAddedWeekCount--;
            }

        }

        // displayed month 시작일/종료일/기준일
        return new DateRange(startDate, endDate, targetDate);
    }


    /**
     * Gets week date range.
     * 화면에 출력할 'week' 기간 산정
     * 기준일의 이전 최근 'weekStartDay'부터 이후 최근 'weekEndDay'까지의 범위이다.
     *
     * @param targetDate 화면출력을 위한 기준일
     *
     * @return DateRange Week 화면 출력을 위한 시작일 / 종료일
     */
    private DateRange getWeekDateRange(LocalDate targetDate)
    {
        LocalDate startDate = targetDate.with(TemporalAdjusters.previousOrSame(calendarProperties.getStartDayOfWeek()));
        LocalDate endDate = targetDate.with(TemporalAdjusters.nextOrSame(calendarProperties.getEndDayOfWeek()));

        return new DateRange(startDate, endDate, targetDate);
    }

    /**
     * Gets day date range.
     * 화면에 출력할 'day' 기간 산정
     * day는 결국 표기하고자 하는 자기자신이므로, 시작일/종료일/기준일 모두 param value이다.
     *
     * @param targetDate 화면출력을 위한 기준일
     *
     * @return DateRange Day 화면 출력을 위한 시작일 / 종료일
     */
    private DateRange getDayDateRange(LocalDate targetDate)
    {
        return new DateRange(targetDate, targetDate, targetDate);
    }
}
