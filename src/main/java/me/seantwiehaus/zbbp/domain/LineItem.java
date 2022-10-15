package me.seantwiehaus.zbbp.domain;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

/**
 * @param transactions Unmodifiable List
 */
public record LineItem(
    Long id,
    YearMonth budgetDate,
    String name,
    int plannedAmount,
    Category category,
    String description,
    Instant lastModifiedAt,
    List<Transaction> transactions) {
  public LineItem {
    transactions = transactions != null ? List.copyOf(transactions) : List.of();
  }

  public int calculateTotalTransactions() {
    return transactions
        .stream()
        .mapToInt(Transaction::getAmount)
        .sum();
  }

  public double calculatePercentageOfPlanned() {
    // Without this check, plannedAmount could be 0 in the divisor below.
    if (plannedAmount == 0) {
      // If plannedAmount is 0, but totalTransactions is 5.50(converted to dollars) the percentage should be %105.50
      return 100.0 + calculateTotalTransactions() * 100;
    }
    return calculateTotalTransactions() * 100.0 / plannedAmount;
  }

  public int calculateTotalRemaining() {
    return plannedAmount - calculateTotalTransactions();
  }
}
