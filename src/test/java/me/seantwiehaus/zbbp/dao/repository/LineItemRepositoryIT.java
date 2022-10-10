package me.seantwiehaus.zbbp.dao.repository;

import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import me.seantwiehaus.zbbp.domain.Category;
import me.seantwiehaus.zbbp.domain.ItemType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class LineItemRepositoryIT {
  @Autowired
  TestEntityManager entityManager;
  @Autowired
  LineItemRepository lineItemRepository;

  @Nested
  class FindTopByOrderByBudgetDateDesc {
    @Test
    void shouldReturnMostRecentLineItem() {
      // Given two LineItems with different dates
      LineItemEntity shouldBeTop = createLineItemEntity(YearMonth.of(2022, 2));
      entityManager.persistAndFlush(shouldBeTop);
      LineItemEntity shouldNot = createLineItemEntity(YearMonth.of(2022, 1));
      entityManager.persistAndFlush(shouldNot);
      entityManager.clear(); // Clear the context so that entities are not fetched from the first-level cache
      // When findTopByOrderByBudgetDateDesc is called
      Optional<LineItemEntity> mostRecent = lineItemRepository.findTopByOrderByBudgetDateDesc();
      // Then the most recent LineItemEntity should be returned
      assertTrue(mostRecent.isPresent());
      assertEquals(shouldBeTop, mostRecent.get());
      assertEquals(YearMonth.of(2022, 2), mostRecent.get().getBudgetDate());
    }
  }

  @Nested
  class FindAllByBudgetDateBetween {
    @Test
    void shouldReturnLineItemsBetweenTwoDates() {
      // Given three LineItems with different dates
      LineItemEntity shouldBeIncluded = createLineItemEntity(YearMonth.of(2022, 2));
      entityManager.persistAndFlush(shouldBeIncluded);
      LineItemEntity shouldAlsoBeIncluded = createLineItemEntity(YearMonth.of(2022, 1));
      entityManager.persistAndFlush(shouldAlsoBeIncluded);
      LineItemEntity shouldNotBeIncluded = createLineItemEntity(YearMonth.of(2022, 3));
      entityManager.persistAndFlush(shouldNotBeIncluded);
      entityManager.clear(); // Clear the context so that entities are not fetched from the first-level cache
      // When findAllByBudgetDateBetween is called
      List<LineItemEntity> allBetween =
          lineItemRepository.findAllByBudgetDateBetween(YearMonth.of(2022, 1), YearMonth.of(2022, 2));
      // Then only one LineItem should be returned
      assertEquals(2, allBetween.size());
      // And the LineItems for 2022,1 and 2022,2 should be included in the list
      assertTrue(allBetween.stream().allMatch(item -> item.equals(shouldBeIncluded) || item.equals(shouldAlsoBeIncluded)));
      // And the LineItems for 2022,3 should NOT
      assertTrue(allBetween.stream().noneMatch(item -> item.equals(shouldNotBeIncluded)));
    }
  }

  private LineItemEntity createLineItemEntity(YearMonth budgetDate) {
    LineItemEntity lineItemEntity = new LineItemEntity();
    lineItemEntity.setType(ItemType.EXPENSE);
    lineItemEntity.setBudgetDate(budgetDate);
    lineItemEntity.setName("Name");
    lineItemEntity.setPlannedAmount(120000);
    lineItemEntity.setCategory(Category.FOOD);
    return lineItemEntity;
  }
}
