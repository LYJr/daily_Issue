package com.example.daily_issue.calendar.ro;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;

@Data
public class RepeatableTaskReq {

    // 반복 발생 주기
    private Period period;

    // 반복 시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate repeatStartDate;
    // 반복 종료일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate repeatEndDate;

}
