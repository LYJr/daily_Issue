package com.example.daily_issue.calendar.service;

import com.example.daily_issue.calendar.vo.DateRange;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;


/**
 * The type Repeatable task service.
 */
@Service
public class RepeatableTaskService {



    // 일자별 (ex : 2일 간격으로 / 매달 10일)


    // 요일별 (ex : 매주 화요일 / 매주 화요일 2주마다 / 매달 첫번째 화요일)
    private void getTaskListByDayOfWeek(LocalDate baseDate, DayOfWeek dayOfWeek, boolean isIncludeBaseDay)
    {

        // 기준일 포함 가장 최근 설정요일 일자
        LocalDate nearestConditionDate = baseDate.with(TemporalAdjusters.nextOrSame(dayOfWeek));

        // 기준일을 출력에 포함할 것인지. (설정 요일이 아니더라도)
        if(isIncludeBaseDay)
        {
            //if(!nearestConditionDate.isEqual(baseDate))
        }


    }
/*
    private List<LocalDate> getTaskListOfDateRange(int repeatUnit, LocalDate baseDate, DateRange dateRange, boolean includeBaseDate)
    {
        List<LocalDate> tasks = new ArrayList<>();

        //ChronoUnit type = ChronoUnit.MONTHS;

        // get date range



        // include data

        //
        List<LocalDate> dates = new ArrayList<>();
        LocalDate tempBasicStartDate = dateRange.getStartDate();
        while(tempBasicStartDate.isBefore(dateRange.getEndDate())
                || tempBasicStartDate.isEqual(dateRange.getEndDate()))
        {
            dates.add(tempBasicStartDate);
            tempBasicStartDate.plus();
        }



    }*/


    ////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////// 기간 체크 /////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    private boolean isNestedDayInDateRange(ChronoUnit chronoUnit, DateRange displayedDateRange, LocalDate targetDate)
    {
        boolean isNested = false;

        switch (chronoUnit)
        {
            default:
            case MONTHS:
                // display 되는 달
                Month displayMonth = Month.from(displayedDateRange.getBaseDate());
                isNested = displayMonth.equals(targetDate.getMonth());
                break;
            case WEEKS:
                isNested = !targetDate.isBefore(displayedDateRange.getStartDate())
                        && !targetDate.isAfter(displayedDateRange.getEndDate());
                break;
            case DAYS:
                isNested = targetDate.isEqual(displayedDateRange.getBaseDate());
                break;
        }

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
        startDate = startDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        LocalDate endDate = displayDate.with(TemporalAdjusters.lastDayOfMonth());
        endDate = endDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        return new DateRange(startDate, endDate, displayDate);
    }

    /**
     * baseDate에 해당하는 week의 Mon/Sun LocalDate 반환
     * @param displayDate 기준일
     * @return 기준일이 포함된 주의 월요일/일요일 LocalDate
     */
    private DateRange getWeekDateRange(LocalDate displayDate)
    {
        LocalDate startDate = displayDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = displayDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        return new DateRange(startDate, endDate, displayDate);
    }

    private DateRange getDayDateRange(LocalDate displayDate)
    {
        return new DateRange(displayDate, displayDate, displayDate);
    }
}
