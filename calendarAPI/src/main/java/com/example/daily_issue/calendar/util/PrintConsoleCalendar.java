package com.example.daily_issue.calendar.util;

import java.util.Calendar;

public class PrintConsoleCalendar {

    public void printCalendar() {
        Calendar cal = Calendar.getInstance();
        int day[][][] = new int[12][6][7];  //[월][주][요일]
        String week[] = new String[]{
                "일", "월", "화", "수", "목", "금", "토"
        };


        for (int month = 0; month < 12; month++) {
            cal.set(Calendar.MONTH, month);
            //이번달 마지막일 maxDay에 저장
            int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            for (int i = 1; i <= maxDay; i++) {
                cal.set(Calendar.DATE, i);
                //주와 요일은 1부터 시작하기 때문에 0부터 시작하는 배열 인덱스와 맞지 않다
                //그래서 -1을 해서 배열 인덱스와 맞춰 주었다.
                day[cal.get(Calendar.MONTH)]
                        [cal.get(Calendar.WEEK_OF_MONTH) - 1]
                        [cal.get(Calendar.DAY_OF_WEEK) - 1] = i;
            }
        }

        for (int month = 0; month < 12; month++) {
            int mm = month + 1;
            System.out.println("\t\t\t" + mm + "월");

            for (int i = 0; i < week.length; i++) {
                System.out.print(week[i] + "\t");
            }
            System.out.println();

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (day[month][i][j] != 0)
                        System.out.print(day[month][i][j] + "\t");
                    else
                        System.out.print("\t");
                }
                System.out.println();
            }
        }
    }
}
