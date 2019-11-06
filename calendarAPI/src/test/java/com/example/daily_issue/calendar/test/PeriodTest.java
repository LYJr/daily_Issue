package com.example.daily_issue.calendar.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;

public class PeriodTest {
    @Test
    @DisplayName("Period값을 이용한 다음 일자 구하기")
    public void nextDate()
    {
        Period period = Period.ofWeeks(1);
        LocalDate nowDate = LocalDate.now();

        LocalDate nextDate = nowDate.plus(period);

        System.out.println("now : " + nowDate);
        System.out.println("next : " + nextDate);
    }

    @Test
    @DisplayName("해당 윌에 / 해당 period를 가진 일정 list 가져오기")
    public void taskList()
    {
        Period period = Period.ofWeeks(1);
        LocalDate startDate = LocalDate.of(2019, 10, 1);
        LocalDate endDate = LocalDate.of(2019, 10, 31);


        List<LocalDate> dates = new ArrayList<>();

        while(startDate.isBefore(endDate))
        {
            System.out.println(startDate);
            dates.add(startDate);
            startDate = startDate.plus(period);
        }
    }

    @Test
    @DisplayName("해당 월의 마지막 일을 구한다")
    public void getLastDayOfMonth()
    {


        LocalDate date = LocalDate.of(2019, 11, 10);
        // case 1
        LocalDate lastDate = date.with(TemporalAdjusters.lastDayOfMonth());

        // case 2
        ValueRange monthRange = date.range(ChronoField.DAY_OF_MONTH);


        System.out.println(lastDate);
        System.out.println(monthRange);


        System.out.println(date.range(ChronoField.DAY_OF_WEEK));
        System.out.println(date.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH));
        System.out.println(date.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR));
        System.out.println(date.range(ChronoField.ALIGNED_WEEK_OF_MONTH));
        System.out.println(date.range(ChronoField.ALIGNED_WEEK_OF_YEAR));
        System.out.println(date.range(ChronoField.DAY_OF_YEAR));
        System.out.println(date.range(ChronoField.ERA));
    }



    @Test
    @DisplayName("기준일 이후 매주 화요일마다")
    public void getEveryTuesday()
    {
        Period weekPeriod = Period.ofWeeks(1);

        // 조회 기준 월
        LocalDate date = LocalDate.of(2019, 11, 5);

        // get first day of month
        LocalDate firstDayofMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        // get last day of month
        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

        // 해당 월의 첫번째 화요일을 출력
        LocalDate firstTuesdayOfMonth = date.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.TUESDAY));
        // 위와 동일. 단, 위는 몇번째 화요일인지 지정 가능
        LocalDate firstTuesdayOfMonth2 = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.TUESDAY));

        // 기준일 포함 최근 화요일
        LocalDate nearestTuesdayOfTargetDay = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY));

        List<LocalDate> tuesdays = new ArrayList<>();
        if (!nearestTuesdayOfTargetDay.equals(date))
        {
            tuesdays.add(date);
        }

        while (nearestTuesdayOfTargetDay.isBefore(lastDayOfMonth))
        {
            tuesdays.add(nearestTuesdayOfTargetDay);
            // 해당 방법은 2주마다 화요일 등의 설정이 가능하므로, 선호
            nearestTuesdayOfTargetDay = nearestTuesdayOfTargetDay.plusWeeks(2);
            //nearestTuesdayOfTargetDay = nearestTuesdayOfTargetDay.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
        }

        tuesdays.forEach(System.out::println);
    }

    @Test
    @DisplayName("기준일 이후 매일단위")
    public void test()
    {
        // 조회 기준 월
        LocalDate date = LocalDate.of(2019, 11, 6);


        List<LocalDate> everyDay = new ArrayList<>();

        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());


        while (date.isBefore(lastDayOfMonth) || date.isEqual(lastDayOfMonth))
        {
            everyDay.add(date);
            date = date.plusDays(1);
        }

        everyDay.forEach(System.out::println);
    }


    @Test
    @DisplayName("기준일 기준 해당 주 목록 출력 (시작 : 월)")
    public void currentWeekList()
    {
        LocalDate baseDate = LocalDate.of(2019, 11, 5);
        LocalDate mondayOfWeek = baseDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sundayOfWeek = baseDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        System.out.println(mondayOfWeek);
        System.out.println(sundayOfWeek);
    }


    @Test
    @DisplayName("표기하려는 기간에 지정일이 내포되어있는가?")
    public void isNestedDayInDateRange()
    {
        ///////////////////////////////////////////////////////////
        // 월단위
        ///////////////////////////////////////////////////////////
        LocalDate baseDate = LocalDate.of(2019, 11, 11);
        Month month = Month.from(baseDate);

        LocalDate displayDate = LocalDate.of(2019, 11, 10);

        boolean isNested = month.equals(baseDate.getMonth());

        System.out.println(isNested);
        ///////////////////////////////////////////////////////////
        // 주단위
        ///////////////////////////////////////////////////////////
        LocalDate mondayOfWeek = displayDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sundayOfWeek = displayDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        isNested = !baseDate.isBefore(mondayOfWeek) && !baseDate.isAfter(sundayOfWeek);
        System.out.println(isNested);
        ///////////////////////////////////////////////////////////
        // 일단위
        ///////////////////////////////////////////////////////////
        isNested = baseDate.isEqual(displayDate);
        System.out.println(isNested);
    }

}
