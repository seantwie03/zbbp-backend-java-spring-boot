package me.seantwiehaus.zbbp.dao.entity;

import me.seantwiehaus.zbbp.domain.Category;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * There isn't a BaseEntity table so the BaseEntity must be tested indirectly.
 */
@DataJpaTest
class BaseEntityIT {
  @Autowired
  private TestEntityManager entityManager;

  @Nested
  class WhenPersistingTheEntity {
    // shouldSaveLastModifiedDate
    @Test
    void insertLastModifiedAtInNewEntity() {
      // Given a new LineItemEntity
      LineItemEntity lineItemEntity = createLineItemEntity("shouldInsertLastModified");
      // When that entity is persisted
      LineItemEntity saved = entityManager.persistAndFlush(lineItemEntity);
      // Then the lastModifiedAt should be set
      assertNotNull(saved.getLastModifiedAt());
    }

    @Test
    void updateLastModifiedAtWhenUpdatingEntity() {
      // Given a new LineItemEntity
      LineItemEntity lineItemEntity = createLineItemEntity("shouldUpdateLastModified");
      LineItemEntity saved = entityManager.persistAndFlush(lineItemEntity);
      Instant savedLastModifiedAt = saved.getLastModifiedAt();
      // When that lineItemEntity is updated
      saved.setBudgetDate(YearMonth.now().plusMonths(1)); // Randomly chose to update budgetDate
      LineItemEntity updated = entityManager.persistAndFlush(saved);
      // Then the lastModifiedAt should be updated
      assertTrue(updated.getLastModifiedAt().isAfter(savedLastModifiedAt));
    }
  }

  private LineItemEntity createLineItemEntity(String name) {
    LineItemEntity lineItemEntity = new LineItemEntity();
    lineItemEntity.setBudgetDate(YearMonth.now());
    lineItemEntity.setName(name);
    lineItemEntity.setPlannedAmount(120000);
    lineItemEntity.setCategory(Category.HEALTH);
    return lineItemEntity;
  }
}
