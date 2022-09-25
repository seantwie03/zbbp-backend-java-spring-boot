package me.seantwiehaus.zbbp.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LineItemTest {
  @Nested
  class withTransactions {
    private static LineItem lineItem;

    @BeforeAll
    static void setup() {
      List<Transaction> transactions = List.of(
          new Transaction(
              1L,
              LocalDate.now(),
              "Merchant 1",
              new Money(10.50),
              null,
              "Transaction 1",
              null),
          new Transaction(
              1L,
              LocalDate.now(),
              "Merchant 2",
              new Money(11.00),
              null,
              "Transaction 2",
              null),
          new Transaction(
              1L,
              LocalDate.now(),
              "Merchant 3",
              new Money(4.00),
              null,
              "Transaction 3",
              null)
      );
      lineItem = new LineItem(
          1L,
          new BudgetMonth(),
          "Groceries",
          new Money(50.0),
          1L,
          "Description",
          transactions,
          null);
    }

    @Test
    void shouldCalculateCorrectTotal() {
      assertEquals(new Money(25.50), lineItem.getTotalSpent());
    }

    @Test
    void shouldCalculateCorrectPercentageSpent() {
      assertEquals(51.0, lineItem.getPercentageSpent());
    }

    @Test
    void shouldCalculateCorrectAmountRemaining() {
      assertEquals(new Money(24.50), lineItem.getTotalRemaining());
    }

    @Test
    void shouldCalculateCorrectPercentageRemaining() {
      assertEquals(49.0, lineItem.getPercentageRemaining());
    }
  }

  @Nested
  class withoutTransactions {
    private static LineItem lineItem;

    @BeforeAll
    static void setup() {
      lineItem = new LineItem(
          null,
          new BudgetMonth(),
          "Restaurants",
          new Money(50.0),
          1L,
          "Description",
          null,
          null
      );
    }

    @Test
    void shouldCalculateCorrectTotal() {
      assertEquals(new Money(0), lineItem.getTotalSpent());
    }

    @Test
    void shouldCalculateCorrectPercentageSpent() {
      assertEquals(0.0, lineItem.getPercentageSpent());
    }

    @Test
    void shouldCalculateCorrectAmountRemaining() {
      assertEquals(new Money(50.0), lineItem.getTotalRemaining());
    }

    @Test
    void shouldCalculateCorrectPercentageRemaining() {
      assertEquals(100.0, lineItem.getPercentageRemaining());
    }

  }
}
