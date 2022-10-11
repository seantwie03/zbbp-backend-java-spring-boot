package me.seantwiehaus.zbbp.dao.entity;

import me.seantwiehaus.zbbp.domain.ItemType;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineItemEntityTest {
  @Test
  void typeShouldDefaultToExpense() {
    // Given a LineItemEntity is instantiated
    LineItemEntity lineItem = new LineItemEntity();
    // When the type has not been explicitly set
    // Then the type should be Expense
    assertEquals(ItemType.EXPENSE, lineItem.getType());
  }

  @Test
  void budgetDateShouldAlwaysBeTheFirstDayOfTheMonth() {
    // Given a LineItemEntity
    LineItemEntity lineItem = new LineItemEntity();
    // When setBudgetDate is called with any day of the month
    lineItem.setBudgetDate(YearMonth.of(2030, 1));
    // Then the budgetDate should be set to the first day of the same month
    assertEquals(YearMonth.of(2030, 1), lineItem.getBudgetDate());
  }

}