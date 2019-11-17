package com.example.daily_issue.calendar.dao.repository.basic;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-11
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-11)
 */

import com.example.daily_issue.calendar.domain.entity.BasicTaskEntity;
import com.example.daily_issue.calendar.domain.entity.QBasicTaskEntity;
import com.example.daily_issue.calendar.domain.entity.QRepeatableTaskEntity;
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
        QBasicTaskEntity basicTaskEntity = QBasicTaskEntity.basicTaskEntity;

        List<BasicTaskEntity> basicTasks =
            from(basicTaskEntity)
            .where(
                basicTaskEntity.taskOwner.eq(securityService.getMember())
                .and(
                    basicTaskEntity.taskStartDate.between(dateRange.getStartDate(), dateRange.getEndDate())
                )
            )
            .fetch();

        return basicTasks;
    }

    @Override
    public List<RepeatableTaskEntity> findByDisplayableRepeatableTasks(DateRange dateRange) {

        // repeatable task
        QRepeatableTaskEntity repeatableTaskEntity = QRepeatableTaskEntity.repeatableTaskEntity;
        QBasicTaskEntity basicTaskEntity = QBasicTaskEntity.basicTaskEntity;

        List<RepeatableTaskEntity> repeatableTasks =
            from(repeatableTaskEntity)
            .innerJoin(repeatableTaskEntity.basicTask, basicTaskEntity)
            .fetchJoin()
            .where(
                repeatableTaskEntity.basicTask.taskOwner.eq(securityService.getMember())
                .and(
                    //repeatableTask.repeatStartDate.before(dateRange.getEndDate())
                    repeatableTaskEntity.repeatStartDate.loe(dateRange.getEndDate())
                )
                .and(
                    repeatableTaskEntity.repeatEndDate.isNull()
                    //.or(repeatableTask.repeatEndDate.after(dateRange.getStartDate()))
                    .or(repeatableTaskEntity.repeatEndDate.goe(dateRange.getStartDate()))
                    )
                )
            .fetch();

        return repeatableTasks;
    }


}
