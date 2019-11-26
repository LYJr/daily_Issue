package com.example.daily_issue.calendar.domain.entity;

import com.example.daily_issue.calendar.domain.entity.listener.AuditableRootListener;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@EntityListeners(value = { AuditingEntityListener.class, AuditableRootListener.class })
@MappedSuperclass
@Getter @Setter
/*public class AuditableRootTask<U, PK extends Serializable> extends AbstractPersistable<PK>
        implements Auditable<U, PK, LocalDateTime>  {*/
public class AuditableRootEntity<U, PK extends Serializable> extends AbstractPersistable<PK> {

    @ManyToOne (fetch = FetchType.LAZY) //
    @JoinColumn(updatable = false, nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @CreatedBy
    private @NonNull U createdBy;

    @Column(updatable = false)
    @CreatedDate
    private @Nullable LocalDateTime createdDate;

    @ManyToOne (fetch = FetchType.LAZY) //
    @JoinColumn(nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @LastModifiedBy
    private @NonNull U lastModifiedBy;

    @LastModifiedDate
    private @Nullable LocalDateTime lastModifiedDate;

}
