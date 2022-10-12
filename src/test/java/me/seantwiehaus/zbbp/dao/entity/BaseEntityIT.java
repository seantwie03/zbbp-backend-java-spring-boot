package me.seantwiehaus.zbbp.dao.entity;

import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.ItemType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.*;

/**
 * There isn't a BaseEntity table so the BaseEntity must be tested indirectly.
 */
@DataJpaTest
class BaseEntityIT {
  @Autowired
  private TestEntityManager entityManager;

  @Nested
  class WhenRetrievingTheEntity {
    @Test
    // SQL script is used because it is not possible to save mixed-case via TestEntityManager.
    @Sql("insertMixedCaseTypeIntoLineItems.sql")
    void shouldRetrieveTypeEnumFromDatabaseWhenRowDatabaseContainsMixedCaseTypeValue() {
      // Given a database record with mixed-case Type and Category (ID = 2 from @SQL)
      // When that record is retrieved from the database
      LineItemEntity lineItem = entityManager.find(LineItemEntity.class, 2L);
      // Then the Enum values should be set correctly
      assertEquals(ItemType.INCOME, lineItem.getType());
    }
  }

  @Nested
  class WhenPersistingTheEntity {
    // shouldSaveLastModifiedDate
    @Test
    void shouldInsertLastModifiedAtInNewEntity() {
      // Given a new LineItemEntity
      LineItemEntity lineItemEntity = createLineItemEntity("shouldInsertLastModified");
      // When that entity is persisted
      LineItemEntity saved = entityManager.persistAndFlush(lineItemEntity);
      // Then the lastModifiedAt should be set
      assertNotNull(saved.getLastModifiedAt());
    }

    @Test
    void shouldUpdateLastModifiedAtWhenUpdatingEntity() {
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
    lineItemEntity.setType(ItemType.EXPENSE);
    lineItemEntity.setBudgetDate(YearMonth.now());
    lineItemEntity.setName(name);
    lineItemEntity.setPlannedAmount(120000);
    lineItemEntity.setCategory(Category.HEALTH);
    return lineItemEntity;
  }
}
