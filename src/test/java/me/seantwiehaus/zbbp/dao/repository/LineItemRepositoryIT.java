package me.seantwiehaus.zbbp.dao.repository;

import me.seantwiehaus.zbbp.dao.entity.LineItemEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class LineItemRepositoryIT {
  @Autowired
  LineItemRepository lineItemRepository;

  @Test
  @Sql("insertThreeLineItemsWithDifferentDates.sql")
  void shouldReturnMostRecentLineItem() {
    // Given two LineItems with different dates (@SQL)
    // When findTopByOrderByBudgetDateDesc is called
    Optional<LineItemEntity> mostRecent = lineItemRepository.findTopByOrderByBudgetDateDesc();
    // Then the most recent LineItem (ID = 7878780) should be returned
    assertTrue(mostRecent.isPresent());
    assertEquals(7878780, mostRecent.get().getId());
    assertEquals(LocalDate.of(2022, 2, 1), mostRecent.get().getBudgetDate());
  }

  @Test
  @Sql("insertThreeLineItemsWithDifferentDates.sql")
  void shouldReturnLineItemsBetweenTwoDates() {
    // Given two LineItems with different dates (@SQL)
    // When findAllByBudgetDateBetween is called
    List<LineItemEntity> allBetween =
        lineItemRepository.findAllByBudgetDateBetween(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 2, 1));
    // Then only one LineItem should be returned
    assertEquals(2, allBetween.size());
    assertEquals(7878779, allBetween.get(0).getId());
    assertEquals(LocalDate.of(2022, 1, 1), allBetween.get(0).getBudgetDate());
    assertEquals(7878780, allBetween.get(1).getId());
    assertEquals(LocalDate.of(2022, 2, 1), allBetween.get(1).getBudgetDate());
  }
}
