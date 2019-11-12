package com.example.daily_issue.calendar.dao.repository.basic;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-11
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-11)
 */

import com.example.daily_issue.calendar.domain.QBasicTask;
import com.example.daily_issue.calendar.domain.QRepeatableTask;
import com.example.daily_issue.calendar.domain.entity.BasicTaskEntity;
import com.example.daily_issue.calendar.domain.entity.RepeatableTaskEntity;
import com.example.daily_issue.calendar.domain.vo.DateRange;
import com.example.daily_issue.calendar.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

/**
 *
 *
 */
class BasicTaskCustomRepositoryImpl extends QuerydslRepositorySupport implements BasicTaskCustomRepository{

    @Autowired
    SecurityService securityService;

    public BasicTaskCustomRepositoryImpl() {
        super(BasicTaskEntity.class);
    }

    @Override
    public List<BasicTaskEntity> findByDisplayableBasicTasks(DateRange dateRange) {

        // basic task
        QBasicTask basicTask = QBasicTask.basicTask;

        List<BasicTaskEntity> basicTasks =
            from(basicTask)
            .where(
                basicTask.taskOwner.eq(securityService.getMember())
                .and(
                    basicTask.taskStartDate.between(dateRange.getStartDate(), dateRange.getEndDate())
                )
            )
            .fetch();

        return basicTasks;
    }

    @Override
    public List<RepeatableTaskEntity> findByDisplayableRepeatableTasks(DateRange dateRange) {

        // repeatable task
        QRepeatableTask repeatableTask = QRepeatableTask.repeatableTask;
        QBasicTask basicTask = QBasicTask.basicTask;

        List<RepeatableTaskEntity> repeatableTasks =
            from(repeatableTask)
            .innerJoin(repeatableTask.basicTask, basicTask)
            .fetchJoin()
            .where(
                repeatableTask.basicTask.taskOwner.eq(securityService.getMember())
                .and(
                    //repeatableTask.repeatStartDate.before(dateRange.getEndDate())
                    repeatableTask.repeatStartDate.loe(dateRange.getEndDate())
                )
                .and(
                    repeatableTask.repeatEndDate.isNull()
                    //.or(repeatableTask.repeatEndDate.after(dateRange.getStartDate()))
                    .or(repeatableTask.repeatEndDate.goe(dateRange.getStartDate()))
                    )
                )
            .fetch();

        return repeatableTasks;
    }


}
