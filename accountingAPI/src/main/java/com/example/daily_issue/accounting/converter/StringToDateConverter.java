package com.example.daily_issue.accounting.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDate;

/**
 * yyyy-MM-dd 형식의 {@link java.lang.String}을 {@link java.time.LocalDate}로 변환하는 컨버터
 * 이 컨버터는 빈이므로 자동으로 {@link org.springframework.core.convert.ConversionService}에 등록된다.
 *
 * @see org.springframework.core.convert.converter.Converter
 *
 * @author 진환
 */
@Component
public class StringToDateConverter implements Converter<String, LocalDate> {

    /**
     * source를 LocalDate로 변환한다.
     * @param source 변환할 문자열
     * @return 문자열에서 파싱한 LocalDate
     */
    @Override
    public LocalDate convert(String source) {
        int year;
        int month;
        int day;

        if(source.matches("\\d{4}-\\d{2}-\\d{2}"))
            throw new IllegalArgumentException("String not fit for format, String to LocalDate");

        String[] date = source.split("-");
        year = Integer.parseInt(date[0]);
        month = Integer.parseInt(date[1]);
        day = Integer.parseInt(date[2]);

        return LocalDate.of(year,month, day);
    }
}
