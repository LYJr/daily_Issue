package com.example.daily_issue.calendar.dao.repository.basic;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-11
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-11)
 */

import com.example.daily_issue.calendar.domain.entity.BasicTaskEntity;
import com.example.daily_issue.calendar.domain.entity.RepeatableTaskEntity;
import com.example.daily_issue.calendar.domain.vo.DateRange;

import java.util.List;

/**
 *
 *
 */
interface BasicTaskCustomRepository {

    List<BasicTaskEntity> findByDisplayableBasicTasks(DateRange dateRange);

    List<RepeatableTaskEntity> findByDisplayableRepeatableTasks(DateRange dateRange);
}
