package com.example.daily_issue.calendar.ro;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;

@Data
public class ReserveReq {

    // 예약 발생 주기 (기본 : 한달단위)
    private Period period;

    // 예약 시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reserveStartDate;
    // 예약 종료일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reserveEndDate;

}
