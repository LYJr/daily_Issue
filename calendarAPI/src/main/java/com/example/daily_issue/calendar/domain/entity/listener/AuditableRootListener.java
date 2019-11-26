package com.example.daily_issue.calendar.domain.entity.listener;/**
 * - "portfolio" Project -
 * Created by blackychris24@gmail.com on 2019-11-11
 * Github : https://github.com/blackychris24
 *
 * @author : BlackyChris
 * @since : 0.0.1-SNAPSHOT (2019-11-11)
 */

import com.example.daily_issue.calendar.domain.entity.BasicTaskEntity;

import javax.persistence.PrePersist;

/**
 *
 *
 */
public class AuditableRootListener {
    @PrePersist
    public void prePersist(Object obj)
    {
        if(obj instanceof BasicTaskEntity)
        {
            BasicTaskEntity entity = (BasicTaskEntity) obj;
            /*if(entity.getCreatedBy().isPresent())
            {
                entity.setTaskOwner(entity.getCreatedBy().get());
            }*/
            if(entity.getCreatedBy() != null)
            {
                entity.setTaskOwner(entity.getCreatedBy());
            }
        }
    }
}
