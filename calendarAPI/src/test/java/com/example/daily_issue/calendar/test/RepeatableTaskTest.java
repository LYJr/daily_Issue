package com.example.daily_issue.calendar.test;

import com.example.daily_issue.calendar.vo.DateRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-06
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-06)
 */
public class RepeatableTaskTest {

    //////////// 조회 기준 / 범위 설정
    // 주의 시작은 월요일 / 주의 종료는 일요일 (월~일 / 일~토 두가지만 선택가능하다)
    private DayOfWeek startDayOfWeek = DayOfWeek.MONDAY;
    private DayOfWeek endDayOfWeek = DayOfWeek.SUNDAY;


    // 설정일
    private LocalDate targetDate = LocalDate.of(2019, 11, 8);

    // 표기 단위 (일 / 주 / 월 / 년)
    private ChronoUnit displayChronoUnit = ChronoUnit.MONTHS;

    // 표기일 (현재 표기하고자 하는 기준일 / ex : 월단위 : 2019. 11. 01)
    private LocalDate displayDate = LocalDate.now();
    // 표기단위에 따른 일자 범위 (기준일에 해당하는 일 / 주 / 월을 계산.)
    // 가령 기준일이 2019.11.06일이면 (일 : 06 ~ 06 / 주 : 04 ~ 10 / 월 : 01 ~ 30)
    private DateRange displayDateRange = getDisplayDateRange(displayChronoUnit, displayDate);


    ///////////// 반복관련 설정
    // 반복 요일 설정 (요일별 반복일 때만 의미가 있다. / 매주 화요일마다 발생)
    DayOfWeek repeatDayOfWeek = DayOfWeek.TUESDAY;
    // 주기 (ex : 1주마다 반복 / 매달 화요일 2주간격으로 반복)
    // 주기 단위
    ChronoUnit repeatChronoUnit = ChronoUnit.WEEKS;
    // 주기 횟수
    int repeatAmount = 1;
    // 설정일 포함여부
    boolean isIncludeBaseDate = true;


    @BeforeEach
    public void setUp()
    {
        // 월 단위 표기
        displayChronoUnit = ChronoUnit.WEEKS;
        // 기준일과 기준일에 해당하는 범위
        displayDate = LocalDate.now();
        displayDateRange = getDisplayDateRange(displayChronoUnit, displayDate);
        // 매주 화요일 반복
        repeatAmount = 1;
        repeatChronoUnit = ChronoUnit.WEEKS;
        repeatDayOfWeek = DayOfWeek.TUESDAY;
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
        repeatDayOfWeek = DayOfWeek.TUESDAY;

        //////////////////////////////////////////////////////////////////////
        ///////// WHEN
        //////////////////////////////////////////////////////////////////////
        // 2. 로직
        // 표기 범위 중에서 가장 가까운 설정요일 검색
        LocalDate nearestDayOfWeek = displayDateRange.getStartDate()
                .with(TemporalAdjusters.nextOrSame(repeatDayOfWeek));


        LocalDate tempDisplayStartDate = displayDateRange.getStartDate();

        // 표기범위내 반복주기만큼 list에 담아낸다
        while(nearestDayOfWeek.isBefore(displayDateRange.getEndDate())
                || nearestDayOfWeek.isEqual(displayDateRange.getEndDate()))
        {
            dates.add(nearestDayOfWeek);
            nearestDayOfWeek = nearestDayOfWeek.plus(repeatAmount, repeatChronoUnit);
        }

        // 설정일을 포함하는 설정이고, 표기 목록에 존재하지 않는다면
        if(isIncludeBaseDate && targetDate != null && !dates.contains(targetDate))
        {
            // 만일 현재 표기 범위내에 설정일이 존재한다면
            if(isNestedDayInDateRange(displayDateRange, targetDate))
            {
              dates.add(targetDate);
            }
        }

        System.out.println(dates);
    }




    ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// 기간 체크 /////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
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
