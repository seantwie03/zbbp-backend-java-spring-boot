package me.seantwiehaus.zbbp.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Column(name = "version", nullable = false)
    @Version
    protected int version;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    protected Instant createdAt;

    @Column(name = "last_modified_at")
    @LastModifiedDate
    protected Instant lastModifiedAt;
}
