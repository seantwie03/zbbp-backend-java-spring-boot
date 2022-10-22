package me.seantwiehaus.zbbp.dao.entity;

import me.seantwiehaus.zbbp.dao.config.JpaAuditingConfiguration;
import me.seantwiehaus.zbbp.domain.Category;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import java.time.Instant;
import java.time.YearMonth;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

/**
 * There isn't a BaseEntity table so the BaseEntity must be tested indirectly.
 */
@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = ASSIGNABLE_TYPE,
    classes = { JpaAuditingConfiguration.class }
))
class BaseEntityIT {
  @Autowired
  private TestEntityManager entityManager;

  @Nested
  class WhenPersistingTheEntity {
    // shouldSaveLastModifiedDate
    @Test
    void insertLastModifiedAtInNewEntity() {
      // Given a new LineItemEntity
      LineItemEntity lineItemEntity = createEntity().build();
      // When that entity is persisted
      LineItemEntity saved = entityManager.persistAndFlush(lineItemEntity);
      // Then the lastModifiedAt should be set
      assertNotNull(saved.getLastModifiedAt());
    }

    @Test
    void updateLastModifiedAtWhenUpdatingEntity() {
      // Given a new LineItemEntity
      LineItemEntity lineItemEntity = createEntity().build();
      LineItemEntity saved = entityManager.persistAndFlush(lineItemEntity);
      Instant savedLastModifiedAt = saved.getLastModifiedAt();
      // When that lineItemEntity is updated
      saved.setBudgetDate(YearMonth.now().plusMonths(1)); // Randomly chose to update budgetDate
      LineItemEntity updated = entityManager.persistAndFlush(saved);
      // Then the lastModifiedAt should be updated
      assertTrue(updated.getLastModifiedAt().isAfter(savedLastModifiedAt));
    }
  }

  private LineItemEntity.LineItemEntityBuilder<?, ?> createEntity() {
    return LineItemEntity
        .builder()
        .budgetDate(YearMonth.now())
        .name("Groceries " + UUID.randomUUID()) // UUID to ensure uniqueness
        .plannedAmount(120000)
        .category(Category.FOOD)
        .description("Description");
  }
}
