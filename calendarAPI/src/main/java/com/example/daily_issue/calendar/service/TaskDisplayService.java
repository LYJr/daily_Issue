package com.example.daily_issue.calendar.service;

import com.example.daily_issue.calendar.domain.entity.RepeatableTaskEntity;
import com.example.daily_issue.calendar.domain.vo.DateRange;
import com.example.daily_issue.calendar.domain.vo.req.DisplayReq;
import com.example.daily_issue.calendar.service.util.CalendarCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Set;

// TODO: 2019-11-08 반복일정에 대한 CRUD만 존재해야 한다. (display관련은 calendar에서)

/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-07
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-07)
 */
/**
 * 반복일정을 위한 CRUD Service
 */
// TODO: 2019-11-10 public 말고... package protected 한정자로 변경해야 한다... 지금은 test class 때문에;;;
@Service
public class TaskDisplayService {

    /**
     * The Calculator.
     * 계산을 위한 Utility class
     *
     * {@link CalendarCalculator} 일정 계산을 위한 Utility class
     */
    @Autowired
    CalendarCalculator calculator;


    public DateRange getDisplayDateRange(DisplayReq displayReq)
    {
        return getDisplayDateRange(displayReq.getDisplayChronoUnit(), displayReq.getDisplayDate());
    }

    public DateRange getDisplayDateRange(ChronoUnit typeChronoUnit, LocalDate baseDate)
    {
        return calculator.getDisplayDateRange(typeChronoUnit, baseDate);
    }

    private DateRange getTaskableDateRange(DisplayReq displayReq, RepeatableTaskEntity repeatableTask)
    {
        return getTaskableDateRange(displayReq.getDisplayChronoUnit(), displayReq.getDisplayDate(), repeatableTask);
    }

    private DateRange getTaskableDateRange(ChronoUnit typeChronoUnit, LocalDate baseDate, RepeatableTaskEntity repeatableTask)
    {
        DateRange displayDateRange = getDisplayDateRange(typeChronoUnit, baseDate);
        return getTaskableDateRange(displayDateRange, repeatableTask);
    }

    private DateRange getTaskableDateRange(DateRange displayDateRange, RepeatableTaskEntity repeatableTask)
    {
        return calculator.getTaskableDateRange(displayDateRange, repeatableTask);
    }












    /**
     * List repeated task by day of weeks set.
     * 설정된 요일마다 반복
     *
     * @param displayDateRange  {@link DateRange} 객체에는 displayDate / repeatDate를 계산한 출력가능한 범위의 기간이 포함
     * @param repeatableTask    {@link RepeatableTaskEntity} entity에서 일정 및 반복과 관련된 정보를 획득
     *
     * @return {@link LocalDate} 결과 일정 목록
     */
    public Set<LocalDate> listRepeatedTaskByDayOfWeeks(DateRange displayDateRange, RepeatableTaskEntity repeatableTask)
    {
        DateRange taskableDateRange = getTaskableDateRange(displayDateRange, repeatableTask);

        Set<LocalDate> dates = new HashSet<>();
        //Period taskPeriod = Period.between(repeatableTask.getBasicTask().getTaskStartDate(), repeatableTask.getBasicTask().getTaskEndDate());

        // 설정된 요일마다
        repeatableTask.getRepeatDayOfWeeks().forEach(week -> {
            // 표기 범위 중에서 가장 가까운 설정요일 검색 (first index)
            LocalDate nearestDayOfWeek = taskableDateRange.getStartDate()
                    .with(TemporalAdjusters.nextOrSame(week));

            // 표기범위내에 존재한다면, add + amount만큼 증가
            while (calculator.isNestedDayInDateRange(taskableDateRange, nearestDayOfWeek))
            {
                dates.add(nearestDayOfWeek);
                nearestDayOfWeek = nearestDayOfWeek.plus(repeatableTask.getRepeatAmount(), ChronoUnit.WEEKS);
            }
        });

        // TODO: 2019-11-09 시작일을 기점으로 task list를 반환하는 것은, calendar service에서 할 일.
        /*Set<RepeatableTask> result = new HashSet<>();
        dates.forEach(startDate -> {
            RepeatableTask temp = new RepeatableTask();
            BeanUtils.copyProperties(task, temp);
            temp.setTaskStartDate(startDate);
            temp.setTaskEndDate(startDate.plus(taskPeriod));
            result.add(temp);
        });*/

        return dates;
    }

    /**
     * List repeated task by specified days set.
     * 설정된 특정일 마다 반복
     *
     * @param displayDateRange  {@link DateRange} 객체에는 displayDate / repeatDate를 계산한 출력가능한 범위의 기간이 포함
     * @param repeatableTask    {@link RepeatableTaskEntity} entity에서 일정 및 반복과 관련된 정보를 획득
     *
     * @return {@link LocalDate} 결과 일정 목록
     */
    public Set<LocalDate> listRepeatedTaskBySpecifiedDays(DateRange displayDateRange, RepeatableTaskEntity repeatableTask)
    {
        DateRange taskableDateRange = getTaskableDateRange(displayDateRange, repeatableTask);

        Set<LocalDate> dates = new HashSet<>();

        repeatableTask.getRepeatDays().forEach(day -> {
            LocalDate targetDate = taskableDateRange.getBaseDate().withDayOfMonth(day);
            if(calculator.isNestedDayInDateRange(taskableDateRange, targetDate))
            {
                dates.add(targetDate);
            }
        });

        return dates;
    }

    /**
     * List repeated task by distance set.
     * 설정된 기간 범위만큼 반복
     *
     * @param displayDateRange  {@link DateRange} 객체에는 displayDate / repeatDate를 계산한 출력가능한 범위의 기간이 포함
     * @param repeatableTask    {@link RepeatableTaskEntity} entity에서 일정 및 반복과 관련된 정보를 획득
     *
     * @return {@link LocalDate} 결과 일정 목록
     */
    public Set<LocalDate> listRepeatedTaskByDistance(DateRange displayDateRange, RepeatableTaskEntity repeatableTask)
    {
        DateRange taskableDateRange = getTaskableDateRange(displayDateRange, repeatableTask);

        Set<LocalDate> dates = new HashSet<>();

        // temporary index date ( 반복 시작일 )
        LocalDate tempIndexDate = taskableDateRange.getStartDate();

        // 일정 시작일과의 차이를 구하고
        // (반복시작일부터 일정이 기록되어야 하므로, 최초 일정일 시작일과 차이를 구하는 것)
        Period period = Period.between(tempIndexDate, repeatableTask.getBasicTask().getTaskStartDate());



        // 일정 대상일로 index를 옮기기 위함 ( index 조정 )
        int indexDistance = Math.abs(period.getDays()%repeatableTask.getRepeatAmount());
        indexDistance = (indexDistance != 0 && period.isNegative()) ? Math.abs(indexDistance-repeatableTask.getRepeatAmount()) : indexDistance;
        tempIndexDate = tempIndexDate.plusDays(indexDistance);

        while (calculator.isNestedDayInDateRange(taskableDateRange, tempIndexDate))
        {
            dates.add(tempIndexDate);
            tempIndexDate = tempIndexDate.plus(repeatableTask.getRepeatAmount(), repeatableTask.getRepeatChronoUnit());
        }

        return dates;
    }

}
