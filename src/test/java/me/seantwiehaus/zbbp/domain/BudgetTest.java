package me.seantwiehaus.zbbp.domain;

import me.seantwiehaus.zbbp.exception.InternalServerException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BudgetTest {
  @Test
  void sortsLineItemsCorrectlyByCategory() {
    // Given a List of LineItems with one from every Category
    List<LineItem> lineItems = List.of(
            createLineItem().category(Category.INCOME).build(),
            createLineItem().category(Category.SAVINGS).build(),
            createLineItem().category(Category.INVESTMENTS).build(),
            createLineItem().category(Category.HOUSING).build(),
            createLineItem().category(Category.UTILITIES).build(),
            createLineItem().category(Category.TRANSPORTATION).build(),
            createLineItem().category(Category.FOOD).build(),
            createLineItem().category(Category.PERSONAL).build(),
            createLineItem().category(Category.HEALTH).build(),
            createLineItem().category(Category.LIFESTYLE).build()
    );

    // When the Budget is created
    Budget budget = new Budget(YearMonth.of(2023, 3), lineItems);

    // Then the LineItems are sorted correctly
    assertTrue(budget.getIncomes().stream().allMatch(lineItem -> lineItem.category() == Category.INCOME));
    assertTrue(budget.getSavings().stream().allMatch(lineItem -> lineItem.category() == Category.SAVINGS));
    assertTrue(budget.getInvestments().stream().allMatch(lineItem -> lineItem.category() == Category.INVESTMENTS));
    assertTrue(budget.getHousing().stream().allMatch(lineItem -> lineItem.category() == Category.HOUSING));
    assertTrue(budget.getUtilities().stream().allMatch(lineItem -> lineItem.category() == Category.UTILITIES));
    assertTrue(budget.getTransportation().stream().allMatch(lineItem -> lineItem.category() == Category.TRANSPORTATION));
    assertTrue(budget.getFood().stream().allMatch(lineItem -> lineItem.category() == Category.FOOD));
    assertTrue(budget.getPersonal().stream().allMatch(lineItem -> lineItem.category() == Category.PERSONAL));
    assertTrue(budget.getHealth().stream().allMatch(lineItem -> lineItem.category() == Category.HEALTH));
    assertTrue(budget.getLifestyle().stream().allMatch(lineItem -> lineItem.category() == Category.LIFESTYLE));
    assertTrue(budget.getDebts().stream().allMatch(lineItem -> lineItem.category() == Category.DEBTS));
  }

  @Test
  void throwsWhenLineItemsHaveDifferentBudgetYearMonths() {
    // Given a List of LineItems with different YearMonths
    YearMonth yearMonth1 = YearMonth.of(2023, 3);
    YearMonth yearMonth2 = YearMonth.of(2023, 4);
    List<LineItem> lineItems = List.of(
            createLineItem().budgetDate(yearMonth1).build(),
            createLineItem().budgetDate(yearMonth2).build()
    );

    // When the Budget is created
    // Then an InternalServerException is thrown
    assertThrows(InternalServerException.class, () -> new Budget(yearMonth1, lineItems));
  }

  @Test
  void getTotalPlannedIncome() {
    // Given a List of Income LineItems whose plannedAmounts sum to 100_00
    List<LineItem> lineItems = List.of(
            createLineItem().category(Category.INCOME).plannedAmount(10_00).build(),
            createLineItem().category(Category.INCOME).plannedAmount(20_00).build(),
            createLineItem().category(Category.INCOME).plannedAmount(30_00).build(),
            createLineItem().category(Category.INCOME).plannedAmount(40_00).build()
    );

    // When the Budget is created
    Budget budget = new Budget(YearMonth.of(2023, 3), lineItems);

    // Then the total planned income is 100_00
    assertEquals(100_00, budget.getTotalPlannedIncome());
  }

  @Test
  void getTotalPlannedExpense() {
    // Given a List of Expense LineItems whose plannedAmounts sum to 100_00
    List<LineItem> lineItems = List.of(
            createLineItem().category(Category.FOOD).plannedAmount(10_00).build(),
            createLineItem().category(Category.HOUSING).plannedAmount(20_00).build(),
            createLineItem().category(Category.SAVINGS).plannedAmount(30_00).build(),
            createLineItem().category(Category.INVESTMENTS).plannedAmount(40_00).build());

    // When the Budget is created
    Budget budget = new Budget(YearMonth.of(2023, 3), lineItems);

    // Then the total planned expense is 100_00
    assertEquals(100_00, budget.getTotalPlannedExpense());
  }

  @Test
  void getTotalSpent() {
    // Given a List of Expense LineItems whose plannedAmounts sum to 100_00
    List<LineItem> lineItems = List.of(
            createLineItem()
                    .category(Category.FOOD)
                    .transactions(
                            List.of(
                                    createTransaction().amount(20_00).build(),
                                    createTransaction().amount(30_00).build()
                            )).build(),
            createLineItem()
                    .category(Category.HOUSING)
                    .transactions(
                            List.of(
                                    createTransaction().amount(10_00).build(),
                                    createTransaction().amount(40_00).build()
                            )).build()
    );

    // When the Budget is created
    Budget budget = new Budget(YearMonth.of(2023, 3), lineItems);

    // Then the total spent is 100_00
    assertEquals(100_00, budget.getTotalSpent());
  }

  @Test
  void getTotalLeftToBudget() {
    // Given a List of LineItems
    List<LineItem> lineItems = List.of(
            // whose Income plannedAmounts sum to 100_00
            createLineItem().category(Category.INCOME).plannedAmount(10_00).build(),
            createLineItem().category(Category.INCOME).plannedAmount(20_00).build(),
            createLineItem().category(Category.INCOME).plannedAmount(30_00).build(),
            createLineItem().category(Category.INCOME).plannedAmount(40_00).build(),
            // And whose Expense plannedAmounts sum to 80_00
            createLineItem().category(Category.FOOD).plannedAmount(20_00).build(),
            createLineItem().category(Category.HOUSING).plannedAmount(20_00).build(),
            createLineItem().category(Category.TRANSPORTATION).plannedAmount(40_00).build());

    // When the Budget is created
    Budget budget = new Budget(YearMonth.of(2023, 3), lineItems);

    // Then the total planned Income is 100_00
    assertEquals(100_00, budget.getTotalPlannedIncome());
    // And the total planned Expense is 80_00
    assertEquals(80_00, budget.getTotalPlannedExpense());
    // And the total left to budget is 20_00
    assertEquals(20_00, budget.getTotalLeftToBudget());
  }

  @Test
  void getTotalLeftToSpend() {
    // Given a List of LineItems whose plannedAmounts sum to 100_00 and whose transactions amounts sum to 60_00
    List<LineItem> lineItems = List.of(
            createLineItem()
                    .category(Category.FOOD)
                    .plannedAmount(50_00)
                    .transactions(List.of(createTransaction().amount(50_00).build()))
                    .build(),
            createLineItem()
                    .category(Category.UTILITIES)
                    .plannedAmount(10_00)
                    .transactions(List.of(createTransaction().amount(5_00).build()))
                    .build(),
            createLineItem()
                    .category(Category.LIFESTYLE)
                    .plannedAmount(20_00)
                    .transactions(List.of(createTransaction().amount(5_00).build()))
                    .build(),
            createLineItem()
                    .category(Category.TRANSPORTATION)
                    .plannedAmount(20_00)
                    .build());

    // When the Budget is created
    Budget budget = new Budget(YearMonth.of(2023, 3), lineItems);

    // Then the total left to spend is 40_00
    assertEquals(40_00, budget.getTotalLeftToSpend());
  }

  private LineItem.LineItemBuilder createLineItem() {
    return LineItem.builder(YearMonth.of(2023, 3), "Name", 1400_00, Category.FOOD)
            .id(1L)
            .description("Description")
            .lastModifiedAt(Instant.parse("2023-03-10T23:31:04.206157Z"));
  }

  private Transaction.TransactionBuilder createTransaction() {
    return Transaction.builder(LocalDate.now(), "Merchant", 10_00)
            .id(1L)
            .description("Description")
            .lineItemId(1L)
            .lastModifiedAt(Instant.parse("2023-03-10T23:31:04.206157Z"));
  }
}
