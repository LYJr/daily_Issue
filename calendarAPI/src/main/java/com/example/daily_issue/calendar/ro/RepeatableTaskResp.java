package com.example.daily_issue.calendar.ro;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    private LocalDate repeatStartDate;
    // 반복 종료일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate repeatEndDate;

}
