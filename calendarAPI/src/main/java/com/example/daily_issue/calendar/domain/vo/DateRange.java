package com.example.daily_issue.calendar.domain.vo;

import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-06
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-06)
 */
/**
 * 시작일과 종료일을 담는 VO 객체
 */
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class DateRange {

    /*
    startDate : 표기의 시작일
    endDate : 표기의 종료일
    baseDate : 기준일

    예시
    월 표시단위 달력이고,
    매주 월~일을 1주일의 시작과 끝이라 한다면

    현재 2019. 11월을 표기하려면
    startDate : 2019. 10. 28
    endDate : 2019. 12. 08
    baseDate : 2019. 11. 01

    baseDate는 11월임을 알아야 하기 때문에 존재한다.
     */
    @NonNull
    private LocalDate startDate;
    @NonNull
    private LocalDate endDate;
    private LocalDate baseDate;

    private LocalDate getFirstDay(ChronoUnit displayChronoUnit)
    {
        LocalDate firstDay;
        switch (displayChronoUnit)
        {
            default:
            case MONTHS:
                firstDay = getBaseDate().with(TemporalAdjusters.firstDayOfMonth());
                break;
            case WEEKS:
                firstDay = getStartDate();
                break;
            case DAYS:
                firstDay = getBaseDate();
                break;
        }
        return firstDay;
    }
}
