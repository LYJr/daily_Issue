package com.example.daily_issue.calendar.domain;

import com.example.daily_issue.login.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class BasicTask extends AuditableRootTask<Member, Long> {

    public BasicTask(Long id)
    {
        this.setId(id);
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "basicTask")
    private List<RepeatableTask> repeatableTasks;

}
