package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

@Getter
@ToString
public class LineItem {
  private final Long id;
  private final YearMonth budgetDate;
  private final String name;
  private final int plannedAmount;
  private final Category category;
  private final String description;
  private final Instant lastModifiedAt;

  private final int totalTransactions;
  private final double percentageOfPlanned;
  private final int totalRemaining;

  /**
   * Unmodifiable List
   */
  private final List<Transaction> transactions;

  public LineItem(YearMonth budgetDate,
                  String name,
                  int plannedAmount,
                  Category category) {
    this(null, budgetDate, name, plannedAmount, category, null, null, null);
  }

  @SuppressWarnings("java:S107")
  public LineItem(Long id,
                  YearMonth budgetDate,
                  String name,
                  int plannedAmount,
                  Category category,
                  String description,
                  List<Transaction> transactions,
                  Instant lastModifiedAt) {
    this.id = id;
    this.budgetDate = budgetDate;
    this.name = name;
    this.plannedAmount = plannedAmount;
    this.category = category;
    this.description = description;
    this.lastModifiedAt = lastModifiedAt;
    this.transactions = transactions != null ? List.copyOf(transactions) : List.of();

    this.totalTransactions = calculateTotalTransactions();
    this.percentageOfPlanned = calculatePercentageOfPlanned();
    this.totalRemaining = calculateTotalRemaining();
  }

  private int calculateTotalTransactions() {
    return transactions
        .stream()
        .mapToInt(Transaction::getAmount)
        .sum();
  }

  private double calculatePercentageOfPlanned() {
    // Without this check, plannedAmount could be 0 in the divisor below.
    if (plannedAmount == 0) {
      // If plannedAmount is 0, but totalTransactions is 5.50(converted to dollars) the percentage should be %105.50
      return 100.0 + this.totalTransactions * 100;
    }
    return this.totalTransactions * 100.0 / plannedAmount;
  }

  private int calculateTotalRemaining() {
    return plannedAmount - this.totalTransactions;
  }
}
