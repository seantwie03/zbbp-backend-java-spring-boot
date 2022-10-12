package me.seantwiehaus.zbbp.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.seantwiehaus.zbbp.domain.ItemType;
import org.hibernate.annotations.ColumnTransformer;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
  @NotNull
  @Column(name = "type", nullable = false, length = 10)
  @ColumnTransformer(read = "upper(type)", write = "upper(?)")
  @Enumerated(EnumType.STRING)
  protected ItemType type = ItemType.EXPENSE;
  @Column(name = "last_modified_at", nullable = false)
  @LastModifiedDate
  protected Instant lastModifiedAt;
}
