package me.seantwiehaus.zbbp.dao.repository;

import me.seantwiehaus.zbbp.dao.config.JpaAuditingConfiguration;
import me.seantwiehaus.zbbp.dao.entity.BaseEntity;
import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.dao.entity.TransactionEntity;
import me.seantwiehaus.zbbp.domain.Category;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = ASSIGNABLE_TYPE,
    classes = { JpaAuditingConfiguration.class }
))
class LineItemRepositoryIT {
  @Autowired
  TestEntityManager entityManager;
  @Autowired
  LineItemRepository repository;

  private final YearMonth startDate = YearMonth.of(2021, 1);
  private final YearMonth endDate = YearMonth.of(2021, 2);

  @Nested
  class FindAllByBudgetDateBetweenOrderByBudgetDateDesc {

    @Test
    void returnEntitiesBetweenTwoDatesInclusive() {
      // Given two entities inside the inclusive date range
      LineItemEntity shouldBeIncluded1 = createLineItemEntity().budgetDate(startDate).build();
      LineItemEntity shouldBeIncluded2 = createLineItemEntity().budgetDate(endDate).build();
      // And two entities outside the date range
      LineItemEntity shouldNotBeIncluded1 = createLineItemEntity().budgetDate(startDate.minusMonths(1)).build();
      LineItemEntity shouldNotBeIncluded2 = createLineItemEntity().budgetDate(endDate.plusMonths(1)).build();
      // That are persisted out of order
      persistAndFlushList(List.of(shouldNotBeIncluded2, shouldBeIncluded2, shouldBeIncluded1, shouldNotBeIncluded1));

      // When the method under test is called
      List<LineItemEntity> returned = repository.findAllByBudgetDateBetweenOrderByBudgetDateDescCategoryAsc(startDate, endDate);

      // Then two entities should be returned
      assertEquals(2, returned.size());
      // And the two returned entities should be the ones inside the date range
      assertTrue(returned.contains(shouldBeIncluded1));
      assertTrue(returned.contains(shouldBeIncluded2));
      // And the two outside the date range should not be returned
      assertFalse(returned.contains(shouldNotBeIncluded1));
      assertFalse(returned.contains(shouldNotBeIncluded2));
    }

    @Test
    void returnEntitiesInCorrectOrder() {
      // Given two entities with different dates
      LineItemEntity highestDateFirstHighestCategoryFirst = createLineItemEntity()
          .budgetDate(endDate)
          .category(Category.FOOD)
          .build();
      LineItemEntity highestDateLowestCategorySecond = createLineItemEntity()
          .budgetDate(endDate)
          .category(Category.HEALTH)
          .build();
      LineItemEntity lowestDateLast = createLineItemEntity()
          .budgetDate(startDate)
          .category(Category.FOOD)
          .build();
      // That are persisted out of order
      persistAndFlushList(List.of(
          lowestDateLast,
          highestDateLowestCategorySecond,
          highestDateFirstHighestCategoryFirst));

      // When the method under test is called
      List<LineItemEntity> returned =
          repository.findAllByBudgetDateBetweenOrderByBudgetDateDescCategoryAsc(startDate, endDate);

      // Then the LineItems should be returned in the correct order
      assertEquals(highestDateFirstHighestCategoryFirst, returned.get(0));
      assertEquals(highestDateLowestCategorySecond, returned.get(1));
      assertEquals(lowestDateLast, returned.get(2));
    }
  }

  @Nested
  class FindAllByBudgetDateOrderByCategoryAscPlannedAmountDesc {
    @Test
    void returnEntitiesInCorrectOrder() {
      // Given two entities in Food category
      LineItemEntity highestCategoryHighestAmountFirst = createLineItemEntity().plannedAmount(12000).category(Category.FOOD).build();
      LineItemEntity highestCategoryLowestAmountSecond = createLineItemEntity().plannedAmount(11000).category(Category.FOOD).build();
      // And one entity in Health category
      LineItemEntity lowestCategoryThird = createLineItemEntity().category(Category.HEALTH).build();
      // That are persisted out of order
      persistAndFlushList(List.of(
          highestCategoryLowestAmountSecond,
          highestCategoryHighestAmountFirst,
          lowestCategoryThird));

      // When the method under test is called
      List<LineItemEntity> returned =
          repository.findAllByBudgetDateOrderByCategoryAscPlannedAmountDesc(YearMonth.now());

      // Then the LineItems should be returned in the correct order
      assertEquals(highestCategoryHighestAmountFirst, returned.get(0));
      assertEquals(highestCategoryLowestAmountSecond, returned.get(1));
      assertEquals(lowestCategoryThird, returned.get(2));
    }
  }

  @Nested
  class FindTopByOrderByBudgetDateDesc {
    @Test
    void getMostRecentLineItem() {
      // Given two entities with different dates
      LineItemEntity mostRecent = createLineItemEntity().budgetDate(endDate).build();
      LineItemEntity notMostRecent = createLineItemEntity().budgetDate(startDate).build();
      persistAndFlushList(List.of(notMostRecent, mostRecent));

      // When the method under test is called
      Optional<LineItemEntity> returned = repository.findTopByOrderByBudgetDateDesc();

      // Then the entity with the most recent BudgetDate should be returned
      assertTrue(returned.isPresent());
      assertEquals(mostRecent, returned.get());
      // And the budgetDates should match
      assertEquals(mostRecent.getBudgetDate(), returned.get().getBudgetDate());
    }
  }

  @Nested
  class Delete {
    @Test
    void deleteLineItemEntityWithNoTransactions() {
      // Given a LineItem with no Transactions
      LineItemEntity toDelete = createLineItemEntity().build();
      persistAndFlushList(List.of(toDelete));

      // When the method under test is called
      repository.delete(toDelete);

      // Then the LineItemEntity should be deleted
      Optional<LineItemEntity> shouldBeDeleted = repository.findLineItemEntityById(toDelete.getId());
      assertTrue(shouldBeDeleted.isEmpty());
    }

    @Test
    void deleteLineItemDoesNotCascadeOrRemoveOrphanTransactions() {
      // Given a LineItemEntity
      LineItemEntity toDelete = createLineItemEntity().build();
      entityManager.persistAndFlush(toDelete);
      // That has two associated TransactionEntities
      TransactionEntity transaction1 = createTransactionEntity().lineItemId(toDelete.getId()).build();
      entityManager.persist(transaction1);
      TransactionEntity transaction2 = createTransactionEntity().lineItemId(toDelete.getId()).build();
      entityManager.persist(transaction2);
      toDelete.setTransactions(List.of(transaction1, transaction2));
      persistAndFlushList(List.of(toDelete));

      // When the method under test is called
      repository.delete(toDelete);

      // Then the item should be deleted
      Optional<LineItemEntity> shouldBeDeleted = repository.findLineItemEntityById(toDelete.getId());
      assertTrue(shouldBeDeleted.isEmpty());
      // And the Transactions should still exist in the DB
      TransactionEntity shouldExist1 = entityManager.find(TransactionEntity.class, transaction1.getId());
      assertNotNull(shouldExist1);
      TransactionEntity shouldExist2 = entityManager.find(TransactionEntity.class, transaction2.getId());
      assertNotNull(shouldExist2);
    }
  }

  private void persistAndFlushList(List<? extends BaseEntity> entities) {
    entities.forEach(entityManager::persist);
    entityManager.flush();
    entityManager.clear(); // Clear the context so that entities are not fetched from the first-level cache
  }

  private LineItemEntity.LineItemEntityBuilder<?, ?> createLineItemEntity() {
    return LineItemEntity.builder()
        .id(null)
        .budgetDate(YearMonth.now())
        .name("Groceries " + UUID.randomUUID()) // UUID for uniqueness
        .plannedAmount(10000)
        .category(Category.FOOD)
        .description("Description");
  }

  private TransactionEntity.TransactionEntityBuilder<?, ?> createTransactionEntity() {
    return TransactionEntity.builder()
        .id(null)
        .date(LocalDate.now())
        .merchant("Merchant " + UUID.randomUUID()) // UUID for uniqueness
        .amount(2500)
        .lastModifiedAt(Instant.now());
  }
}
