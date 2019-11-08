package com.example.daily_issue.calendar.ro;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-07
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-07)
 */

import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * display와 관련된 요청값.
 * 요청온 값을 기준으로 화면을 구성하고, 화면 범위내의 유효한 일정일자를 반환한다.
 */
@Data
public class DisplayReq {

    // 화면 표시되는 단위 (일단위 / 주단위 / 월단위)
    private ChronoUnit displayChronoUnit;
    // 화면에 표시되는 기준 일자 (일단위,주단위 : 해당일 / 월단위 : 월의 첫번째날)
    private LocalDate displayDate;
}
