package me.seantwiehaus.zbbp.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
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
                      10_50,
                      null,
                      "Transaction 1",
                      null),
              new Transaction(
                      1L,
                      LocalDate.now(),
                      "Merchant 2",
                      11_00,
                      null,
                      "Transaction 2",
                      null),
              new Transaction(
                      1L,
                      LocalDate.now(),
                      "Merchant 3",
                      4_00,
                      null,
                      "Transaction 3",
                      null)
      );
      lineItem = new LineItem(
              1L,
              YearMonth.now(),
              "Groceries",
              50_00,
              Category.FOOD,
              "Description",
              null,
              transactions);
    }

    @Test
    void calculateCorrectTotalTransactions() {
      assertEquals(25_50, lineItem.calculateTotalTransactions());
    }

    @Test
    void calculateCorrectPercentageOfPlanned() {
      assertEquals(51.0, lineItem.calculatePercentageOfPlanned());
    }

    @Test
    void calculateCorrectTotalRemaining() {
      assertEquals(24_50, lineItem.calculateTotalRemaining());
    }
  }

  @Nested
  class withoutTransactions {
    private static LineItem lineItem;

    @BeforeAll
    static void setup() {
      lineItem = new LineItem(
              null,
              YearMonth.now(),
              "Restaurants",
              50_00,
              Category.FOOD,
              "Description",
              null,
              null
      );
    }

    @Test
    void calculateCorrectTotalTransactions() {
      assertEquals(0, lineItem.calculateTotalTransactions());
    }

    @Test
    void calculateCorrectPercentageOfPlanned() {
      assertEquals(0, lineItem.calculatePercentageOfPlanned());
    }

    @Test
    void calculateCorrectTotalRemaining() {
      assertEquals(50_00, lineItem.calculateTotalRemaining());
    }
  }
}
