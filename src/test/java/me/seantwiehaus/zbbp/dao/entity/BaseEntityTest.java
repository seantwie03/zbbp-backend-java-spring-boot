package me.seantwiehaus.zbbp.dao.entity;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseEntityTest {
  @Nested
  class ModifiedAfter {
    @Test
    void returnsFalseWhenLastModifiedAtIsNull() {
      // Given a BaseEntity with a null lastModifiedAt
      BaseEntity entity = new BaseEntity();
      entity.setLastModifiedAt(null);

      // When modifiedAfter is called
      // Then false is returned
      assertFalse(entity.modifiedAfter(Instant.now()));
    }

    @Test
    void returnsFalseWhenLastModifiedAtIsBeforeParameter() {
      // Given a BaseEntity with a lastModifiedAt of yesterday
      BaseEntity entity = new BaseEntity();
      entity.setLastModifiedAt(Instant.now().minus(1, java.time.temporal.ChronoUnit.DAYS));

      // When modifiedAfter is called with an instant of today
      // Then false is returned
      assertFalse(entity.modifiedAfter(Instant.now()));
    }

    @Test
    void returnsTrueWhenLastModifiedAtIsAfterParameter() {
      // Given a BaseEntity with a lastModifiedAt of now
      BaseEntity entity = new BaseEntity();
      entity.setLastModifiedAt(Instant.now());

      // When modifiedAfter is called with an instant of now minus 1 minute
      // Then true is returned
      assertTrue(entity.modifiedAfter(Instant.now().minus(1, java.time.temporal.ChronoUnit.MINUTES)));
    }
  }
}
