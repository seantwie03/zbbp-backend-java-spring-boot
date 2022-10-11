package me.seantwiehaus.zbbp.dao.entity;

import me.seantwiehaus.zbbp.domain.ItemType;
import org.junit.jupiter.api.Test;

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
}
