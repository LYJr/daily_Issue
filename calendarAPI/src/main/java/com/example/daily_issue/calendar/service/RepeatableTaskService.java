package com.example.daily_issue.calendar.service;

import com.example.daily_issue.calendar.domain.RepeatableTask;
import com.example.daily_issue.calendar.service.util.CalendarCalculator;
import com.example.daily_issue.calendar.vo.DateRange;
import org.springframework.beans.BeanUtils;
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
 *
 */
@Service
public class RepeatableTaskService {

    /**
     * The Calculator.
     * 계산을 위한 Utility class
     *
     * @see CalendarCalculator
     * {@link CalendarCalculator}
     */
    @Autowired
    CalendarCalculator calculator;


    /**
     * List repeated task by day of weeks set.
     * 설정된 요일마다 반복
     *
     * @param taskableDateRange {@link DateRange} 객체에는 displayDate / repeatDate를 계산한 출력가능한 범위의 기간이 포함
     * @param task              {@link RepeatableTask} entity에서 일정 및 반복과 관련된 정보를 획득
     *
     * @return {@link RepeatableTask} 결과 일정 목록
     */
    public Set<RepeatableTask> listRepeatedTaskByDayOfWeeks(DateRange taskableDateRange, RepeatableTask task)
    {
        Set<LocalDate> dates = new HashSet<>();
        Period taskPeriod = Period.between(task.getTaskStartDate(), task.getTaskEndDate());

        // 설정된 요일마다
        task.getRepeatDayOfWeeks().forEach(week -> {
            // 표기 범위 중에서 가장 가까운 설정요일 검색 (first index)
            LocalDate nearestDayOfWeek = taskableDateRange.getStartDate()
                    .with(TemporalAdjusters.nextOrSame(week));

            // 표기범위내에 존재한다면, add + amount만큼 증가
            while (calculator.isNestedDayInDateRange(taskableDateRange, nearestDayOfWeek))
            {
                dates.add(nearestDayOfWeek);
                nearestDayOfWeek = nearestDayOfWeek.plus(task.getRepeatAmount(), ChronoUnit.WEEKS);
            }
        });

        Set<RepeatableTask> result = new HashSet<>();
        dates.forEach(startDate -> {
            RepeatableTask temp = new RepeatableTask();
            BeanUtils.copyProperties(task, temp);
            temp.setTaskStartDate(startDate);
            temp.setTaskEndDate(startDate.plus(taskPeriod));
            result.add(temp);
        });

        return result;
    }

    /**
     * List repeated task by specified days set.
     *
     * @param taskableDateRange the taskable date range
     * @param task              the task
     *
     * @return the set
     */
    public Set<LocalDate> listRepeatedTaskBySpecifiedDays(DateRange taskableDateRange, RepeatableTask task)
    {
        Set<LocalDate> dates = new HashSet<>();

        task.getRepeatDays().forEach(day -> {
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
     *
     * @param taskableDateRange the taskable date range
     * @param task              the task
     *
     * @return the set
     */
    public Set<LocalDate> listRepeatedTaskByDistance(DateRange taskableDateRange, RepeatableTask task)
    {
        Set<LocalDate> dates = new HashSet<>();

        // temporary index date ( 반복 시작일 )
        LocalDate tempIndexDate = taskableDateRange.getStartDate();

        // 일정 시작일과의 차이를 구하고
        // (반복시작일부터 일정이 기록되어야 하므로, 최초 일정일 시작일과 차이를 구하는 것)
        Period period = Period.between(tempIndexDate, task.getTaskStartDate());



        // 일정 대상일로 index를 옮기기 위함 ( index 조정 )
        int indexDistance = Math.abs(period.getDays()%task.getRepeatAmount());
        indexDistance = (indexDistance != 0 && period.isNegative()) ? Math.abs(indexDistance-task.getRepeatAmount()) : indexDistance;
        tempIndexDate = tempIndexDate.plusDays(indexDistance);

        while (calculator.isNestedDayInDateRange(taskableDateRange, tempIndexDate))
        {
            dates.add(tempIndexDate);
            tempIndexDate = tempIndexDate.plus(task.getRepeatAmount(), task.getRepeatChronoUnit());
        }

        return dates;
    }

}
