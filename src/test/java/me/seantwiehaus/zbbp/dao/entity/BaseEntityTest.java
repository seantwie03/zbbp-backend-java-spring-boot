package me.seantwiehaus.zbbp.dao.entity;

import me.seantwiehaus.zbbp.domain.ItemType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseEntityTest {
  @Test
  void defaultItemTypeIsExpense() {
    // Given a BaseEntity is instantiated
    BaseEntity baseEntity = new LineItemEntity();

    // When the type has not been explicitly set

    // Then the type should be ItemType.EXPENSE
    assertEquals(ItemType.EXPENSE, baseEntity.getType());
  }
}
