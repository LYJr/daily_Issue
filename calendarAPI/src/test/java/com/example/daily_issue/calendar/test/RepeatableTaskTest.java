package com.example.daily_issue.calendar.test;

import com.example.daily_issue.calendar.vo.DateRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-06
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-06)
 */
public class RepeatableTaskTest {

    //////////////////////////////////////////////////////////////////////
    ////////// application property에 설정될 값
    //////////////////////////////////////////////////////////////////////
    //////////// 조회 기준 / 범위 설정
    // 주의 시작은 월요일 / 주의 종료는 일요일 (월~일 / 일~토 두가지만 선택가능하다)
    private DayOfWeek startDayOfWeek = DayOfWeek.MONDAY;
    private DayOfWeek endDayOfWeek = DayOfWeek.SUNDAY;

    // 월별 캘린더에서 한번에 표시될 week 갯수 (6주차)
    private int weekCountWithMonth = 6;


    //////////////////////////////////////////////////////////////////////
    ////////// view 단에서 표시와 관련하여 요청하는 값
    //////////////////////////////////////////////////////////////////////
    // 표기 단위 (일 / 주 / 월 / 년)
    private ChronoUnit displayChronoUnit = ChronoUnit.MONTHS;
    // 표기일 (현재 표기하고자 하는 기준일 / ex : 월단위 : 2019. 11. 01)
    private LocalDate displayDate = LocalDate.now();
    // 표기단위에 따른 일자 범위 (기준일에 해당하는 일 / 주 / 월을 계산.)
    // 가령 기준일이 2019.11.06일이면 (일 : 06 ~ 06 / 주 : 04 ~ 10 / 월 : 01 ~ 30)
    private DateRange displayDateRange = getDisplayDateRange(displayChronoUnit, displayDate);


    //////////////////////////////////////////////////////////////////////
    ////////// Logic에서 필요한 값
    //////////////////////////////////////////////////////////////////////
    private DateRange taskableDateRange;


    //////////////////////////////////////////////////////////////////////
    ////////// Repeatable DTO에 설정될 값
    //////////////////////////////////////////////////////////////////////
    // task
    // 일정 등록일
    private LocalDate targetDate = LocalDate.of(2019, 11, 8);
    // 일정 범위 (예시 : 2일 단위)
    private Period taskPeriod = Period.ofDays(2);
    // 일정 종일여부
    private boolean isAllday = false;
    // 일정 시작시 (종일여부가 아닐 경우에 의미)
    private LocalTime taskStartTime = LocalTime.of(14, 25);
    // 일정 종료시 (종일여부가 아닐 경우에 의미)
    private LocalTime taskEndTime = LocalTime.of(12, 34);


    // repeat
    // 시작일은 필수이며, 종료일은 null 혹은 Optional.empty()일 경우 무한임
    // 반복 시작일
    private LocalDate repeatStartDate = LocalDate.of(2019, 10, 5);
    // 반복 종료일
    private LocalDate repeatEndDate = LocalDate.of(2019, 12, 25);


    // 반복 요일 설정 (요일별 반복일 때만 의미가 있다. / 매주 화요일, 목요일마다 발생)
    Set<DayOfWeek> repeatDayOfWeeks = new HashSet<>();
    // 반복일 설정 (일자별 반복일 때만 의미가 있다. / 매달 10일, 24일마다 발생)
    Set<Integer> repeatDays = new HashSet<>();

    // 주기 (ex : 1주마다 반복 / 매달 화요일 2주간격으로 반복)
    // 주기 단위
    ChronoUnit repeatChronoUnit = ChronoUnit.WEEKS;
    // 주기 횟수
    int repeatAmount = 1;
    // 설정일 포함여부
    boolean isIncludeBaseDate = false;


    @BeforeEach
    public void setUp()
    {
        taskableDateRange = getTaskableDateRange(displayDateRange, displayDate);

        // 월 단위 표기
        displayChronoUnit = ChronoUnit.MONTHS;
        // 기준일과 기준일에 해당하는 범위
        displayDate = LocalDate.now();
        displayDateRange = getDisplayDateRange(displayChronoUnit, displayDate);
        // 매주 화요일 반복
        repeatAmount = 1;
        repeatChronoUnit = ChronoUnit.WEEKS;
        repeatDayOfWeeks.add(DayOfWeek.TUESDAY);
        repeatDayOfWeeks.add(DayOfWeek.SATURDAY);
        repeatDays.add(10);
        repeatDays.add(24);
    }


    @Test
    @DisplayName("매주 화요일 마다")
    public void repeatDayOfWeek()
    {
        // return 객체
        List<LocalDate> dates = new ArrayList<>();

        //////////////////////////////////////////////////////////////////////
        ////// GIVEN
        //////////////////////////////////////////////////////////////////////
        // 1. 설정값
        // 매주
        repeatAmount = 1;
        repeatChronoUnit = ChronoUnit.WEEKS;
        // 화요일
        //repeatDayOfWeeks.add(DayOfWeek.TUESDAY);

        //////////////////////////////////////////////////////////////////////
        ///////// WHEN
        //////////////////////////////////////////////////////////////////////
        // 2. 로직

        /*repeatDayOfWeeks.forEach(week -> {
            // 표기 범위 중에서 가장 가까운 설정요일 검색
            LocalDate nearestDayOfWeek = taskableDateRange.getStartDate()
                    .with(TemporalAdjusters.nextOrSame(week));

            // 표기범위내 반복주기만큼 list에 담아낸다
            while(nearestDayOfWeek.isBefore(taskableDateRange.getEndDate())
                    || nearestDayOfWeek.isEqual(taskableDateRange.getEndDate()))
            {
                dates.add(nearestDayOfWeek);
                nearestDayOfWeek = nearestDayOfWeek.plus(repeatAmount, repeatChronoUnit);
            }
        });*/


        // 요일마다
        repeatDayOfWeeks.forEach(week -> {
            // 표기 범위 중에서 가장 가까운 설정요일 검색
            LocalDate nearestDayOfWeek = taskableDateRange.getStartDate()
                    .with(TemporalAdjusters.nextOrSame(week));

            // 표기범위내 반복주기만큼 list에 담아낸다
            while(nearestDayOfWeek.isBefore(taskableDateRange.getEndDate())
                    || nearestDayOfWeek.isEqual(taskableDateRange.getEndDate()))
            {
                dates.add(nearestDayOfWeek);
                nearestDayOfWeek = nearestDayOfWeek.plus(repeatAmount, repeatChronoUnit);
            }
        });
        // 지정일 마다
        repeatDays.forEach(day -> {
            LocalDate targetDate = taskableDateRange.getBaseDate().withDayOfMonth(day);
            if(isNestedDayInDateRange(taskableDateRange, targetDate))
            {
                dates.add(targetDate);
            }
        });
        // 지정일 간격 마다
        // 표기 범위 중에서 가장 가까운 설정요일 검색
        LocalDate tempIndexDate = targetDate;

        // 표기범위내 반복주기만큼 list에 담아낸다
        // 이건... baseDate랑 createDate랑 between한 것을 주기만큼 나눈 것으로 뭔가 계산할 수 있을것 같은데..
        while(tempIndexDate.isBefore(taskableDateRange.getEndDate())
                || tempIndexDate.isEqual(taskableDateRange.getEndDate()))
        {
            dates.add(tempIndexDate);
            tempIndexDate = tempIndexDate.plus(repeatAmount, repeatChronoUnit);
        }




        // 설정일을 포함하는 설정이고, 표기 목록에 존재하지 않는다면
        if(isIncludeBaseDate && targetDate != null && !dates.contains(targetDate))
        {
            // 만일 현재 표기 범위내에 설정일이 존재한다면
            if(isNestedDayInDateRange(taskableDateRange, targetDate))
            {
              dates.add(targetDate);
            }
        }
        Collections.sort(dates);

        dates.forEach(System.out::println);
    }



    ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// 기간 체크 /////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    private DateRange getTaskableDateRange(DateRange displayedDateRange, LocalDate targetDate)
    {
        // 출력시작일자가 반복시작일자보다 이전이면, 시작일은 반복시작일자 / 아닐경우 출력시작일자
        LocalDate startDate = displayedDateRange.getStartDate().isBefore(repeatStartDate) ?
                repeatStartDate : displayedDateRange.getStartDate();
        // 반복종료일자가 없거나 (무한대), 출력종료일자가 반복종료일보다 이전이면, 종료일은 출력종료일자 / 아닐경우 반복종료일자.
        LocalDate endDate = (repeatEndDate == null) || displayedDateRange.getEndDate().isBefore(repeatEndDate) ?
                displayedDateRange.getEndDate() : repeatEndDate;

        return new DateRange(startDate, endDate, targetDate);
    }

    private boolean isNestedDayInDateRange(DateRange displayedDateRange, LocalDate targetDate)
    {
        boolean isNested = !targetDate.isBefore(displayedDateRange.getStartDate())
                && !targetDate.isAfter(displayedDateRange.getEndDate());

        return isNested;
    }


    ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// 기간 산정 /////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////

    private DateRange getDisplayDateRange(ChronoUnit type, LocalDate displayDate)
    {
        // 기준일의 조회기준 시작일/종료일
        DateRange dateRange;
        switch (type)
        {
            default:
            case DAYS:
                dateRange = getDayDateRange(displayDate);
                break;
            case WEEKS:
                dateRange = getWeekDateRange(displayDate);
                break;
            case MONTHS:
                dateRange = getMonthDateRange(displayDate);
                break;
        }

        return dateRange;
    }

    /**
     * baseDate에 해당하는 month의 첫번째/마지막일 LocalDate 반환
     *
     * @param displayDate 기준일
     * @return 기준일이 포함된 주의 월요일/일요일 LocalDate
     */
    private DateRange getMonthDateRange(LocalDate displayDate)
    {
        LocalDate startDate = displayDate.with(TemporalAdjusters.firstDayOfMonth());
        startDate = startDate.with(TemporalAdjusters.previousOrSame(startDayOfWeek));

        LocalDate endDate = displayDate.with(TemporalAdjusters.lastDayOfMonth());
        endDate = endDate.with(TemporalAdjusters.nextOrSame(endDayOfWeek));



        int weekCount = (int) ChronoUnit.WEEKS.between(startDate, endDate) + 1;
        // 표시해야 될 Week 갯수에 모자랄 경우
        if(weekCount < weekCountWithMonth )
        {
            // 추가해야될 주차
            int toAddedWeekCount = weekCountWithMonth-weekCount;
            while(toAddedWeekCount > 0)
            {
                if(toAddedWeekCount%2 == 1)
                {
                    endDate = endDate.plusWeeks(1);
                }
                else
                {
                    startDate = startDate.minusWeeks(1);
                }
                toAddedWeekCount--;
            }

        }

        return new DateRange(startDate, endDate, displayDate);
    }

    /**
     * baseDate에 해당하는 week의 Mon/Sun LocalDate 반환
     * @param displayDate 기준일
     * @return 기준일이 포함된 주의 월요일/일요일 LocalDate
     */
    private DateRange getWeekDateRange(LocalDate displayDate)
    {
        LocalDate startDate = displayDate.with(TemporalAdjusters.previousOrSame(startDayOfWeek));
        LocalDate endDate = displayDate.with(TemporalAdjusters.nextOrSame(endDayOfWeek));

        return new DateRange(startDate, endDate, displayDate);
    }

    private DateRange getDayDateRange(LocalDate displayDate)
    {
        return new DateRange(displayDate, displayDate, displayDate);
    }
}
