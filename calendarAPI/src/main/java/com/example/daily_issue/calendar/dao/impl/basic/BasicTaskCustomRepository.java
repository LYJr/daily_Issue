package com.example.daily_issue.calendar.dao.impl.basic;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-11
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-11)
 */

import com.example.daily_issue.calendar.domain.BasicTask;
import com.example.daily_issue.calendar.domain.RepeatableTask;
import com.example.daily_issue.calendar.vo.DateRange;

import java.util.List;

/**
 *
 *
 */
interface BasicTaskCustomRepository {

    List<BasicTask> findByDisplayableBasicTasks(DateRange dateRange);

    List<RepeatableTask> findByDisplayableRepeatableTasks(DateRange dateRange);
}
