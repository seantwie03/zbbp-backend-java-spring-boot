package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Getter
@ToString
@SuppressWarnings("java:S107")
public class LineItem extends BaseDomain {
  private final Long id;
  @NotNull
  private final BudgetMonth budgetMonth;
  @NotBlank
  private final String name;
  @NotNull
  private final Money plannedAmount;
  @NotNull
  private final Category category;
  private final String description;

  private final Money totalTransactions;
  private final double percentageOfPlanned;
  private final Money totalRemaining;

  /**
   * Unmodifiable List
   */
  private final List<Transaction> transactions;

  public LineItem(@NotNull ItemType type,
                  @NotNull BudgetMonth budgetMonth,
                  @NotNull String name,
                  double plannedAmount,
                  @NotNull Category category) {
    this(null, type, budgetMonth, name, new Money(plannedAmount), category, null, null, null);
  }

  public LineItem(Long id,
                  @NotNull ItemType type,
                  @NotNull BudgetMonth budgetMonth,
                  @NotNull String name,
                  int plannedAmount,
                  @NotNull Category category,
                  String description,
                  List<Transaction> transactions,
                  Instant lastModifiedAt) {
    this(id, type, budgetMonth, name, new Money(plannedAmount), category, description, transactions, lastModifiedAt);
  }

  public LineItem(Long id,
                  @NotNull ItemType type,
                  @NotNull BudgetMonth budgetMonth,
                  @NotNull String name,
                  @NotNull Money plannedAmount,
                  @NotNull Category category,
                  String description,
                  List<Transaction> transactions,
                  Instant lastModifiedAt) {
    super(type, lastModifiedAt);
    this.id = id;
    this.budgetMonth = budgetMonth;
    this.name = name;
    this.plannedAmount = plannedAmount;
    this.category = category;
    this.description = description;
    this.transactions = transactions != null ? Collections.unmodifiableList(transactions) : List.of();

    this.totalTransactions = calculateTotalTransactions();
    this.percentageOfPlanned = calculatePercentageOfPlanned();
    this.totalRemaining = calculateTotalRemaining();
  }

  private Money calculateTotalTransactions() {
    return new Money(
        transactions
            .stream()
            .map(Transaction::getAmount)
            .mapToInt(Money::inCents)
            .sum());
  }

  private double calculatePercentageOfPlanned() {
    // Without this check, plannedAmount could be 0 in the divisor below.
    if (plannedAmount.inCents() == 0) {
      // If plannedAmount is 0, but totalTransactions is 5.50 the percentage should be %105.50
      return 100 + this.totalTransactions.inDollars();
    }
    return this.totalTransactions.inCents() * 100.0 / plannedAmount.inCents();
  }

  private Money calculateTotalRemaining() {
    return new Money(plannedAmount.inCents() - this.totalTransactions.inCents());
  }
}
