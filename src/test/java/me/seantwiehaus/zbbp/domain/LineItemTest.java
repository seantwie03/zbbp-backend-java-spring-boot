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
              ItemType.EXPENSE,
              LocalDate.now(),
              "Merchant 1",
              new MonetaryAmount(10.50),
              null,
              "Transaction 1"),
          new Transaction(
              1L,
              ItemType.EXPENSE,
              LocalDate.now(),
              "Merchant 2",
              new MonetaryAmount(11.00),
              null,
              "Transaction 2",
              null),
          new Transaction(
              1L,
              ItemType.EXPENSE,
              LocalDate.now(),
              "Merchant 3",
              new MonetaryAmount(4.00),
              null,
              "Transaction 3",
              null)
      );
      lineItem = new LineItem(
          1L,
          ItemType.EXPENSE,
          new BudgetMonth(),
          "Groceries",
          new MonetaryAmount(50.0),
          Category.FOOD,
          "Description",
          transactions,
          null);
    }

    @Test
    void shouldCalculateCorrectTotal() {
      assertEquals(new MonetaryAmount(25.50), lineItem.getTotalTransactions());
    }

    @Test
    void shouldCalculateCorrectPercentageSpent() {
      assertEquals(51.0, lineItem.getPercentageOfPlanned());
    }

    @Test
    void shouldCalculateCorrectAmountRemaining() {
      assertEquals(new MonetaryAmount(24.50), lineItem.getTotalRemaining());
    }
  }

  @Nested
  class withoutTransactions {
    private static LineItem lineItem;

    @BeforeAll
    static void setup() {
      lineItem = new LineItem(
          null,
          ItemType.EXPENSE,
          new BudgetMonth(),
          "Restaurants",
          new MonetaryAmount(50.0),
          Category.FOOD,
          "Description",
          null,
          null
      );
    }

    @Test
    void shouldCalculateCorrectTotal() {
      assertEquals(new MonetaryAmount(0), lineItem.getTotalTransactions());
    }

    @Test
    void shouldCalculateCorrectPercentageSpent() {
      assertEquals(0.0, lineItem.getPercentageOfPlanned());
    }

    @Test
    void shouldCalculateCorrectAmountRemaining() {
      assertEquals(new MonetaryAmount(50.0), lineItem.getTotalRemaining());
    }
  }
}
