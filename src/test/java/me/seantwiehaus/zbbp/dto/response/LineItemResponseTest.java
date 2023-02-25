package me.seantwiehaus.zbbp.dto.response;

import me.seantwiehaus.zbbp.domain.Category;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineItemResponseTest {
  @Nested
  class ConstructorThatTakesCents {
    @Test
    void convertsPlannedAmountToDollars() {
      LineItemResponse lineItemResponse = new LineItemResponse(
              1L,
              YearMonth.of(2022, 2),
              "Name",
              3500_99,
              Category.FOOD,
              "description",
              Instant.now(),
              0,
              0.0,
              0,
              List.of());

      assertEquals(3500.99, lineItemResponse.plannedAmount());
    }

    @Test
    void convertsTotalTransactionsToDollars() {
      LineItemResponse lineItemResponse = new LineItemResponse(
              1L,
              YearMonth.of(2022, 2),
              "Name",
              0,
              Category.FOOD,
              "description",
              Instant.now(),
              3500_99,
              0.0,
              0,
              List.of());

      assertEquals(3500.99, lineItemResponse.totalTransactions());
    }

    @Test
    void convertsTotalRemainingToDollars() {
      LineItemResponse lineItemResponse = new LineItemResponse(
              1L,
              YearMonth.of(2022, 2),
              "Name",
              0,
              Category.FOOD,
              "description",
              Instant.now(),
              0,
              0.0,
              3500_99,
              List.of());

      assertEquals(3500.99, lineItemResponse.totalRemaining());
    }
  }
}
