package com.example.daily_issue.calendar.ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Data
public class RepeatableTaskResp extends BasicTaskResp {

    // 반복 단위
    private ChronoUnit repeatChronoUnit = ChronoUnit.MONTHS;

    // 반복 주기
    @Min(1)
    private int repeatAmount = 1;

    // 설정일 포함여부
    private boolean isIncludeBaseDate = false;

    // 반복 시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate repeatStartDate;
    // 반복 종료일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate repeatEndDate;

    // 반복 요일 목록
    private Set<DayOfWeek> repeatDayOfWeeks;

    // 반복 일 목록
    private Set<Integer> repeatDays;
}
