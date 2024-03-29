package me.seantwiehaus.zbbp.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  protected Long id;
  @Column(name = "last_modified_at", nullable = false)
  @LastModifiedDate
  protected Instant lastModifiedAt;

  /**
   * @param instant the instant to compare against
   * @return true if the entity has been modified after the given instant
   */
  public boolean modifiedAfter(Instant instant) {
    return lastModifiedAt != null && lastModifiedAt.isAfter(instant);
  }
}
