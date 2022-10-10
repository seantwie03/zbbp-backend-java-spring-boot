package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;

@Getter
@ToString
public class LineItem extends BaseDomain {
  private final Long id;
  @NotNull
  private final YearMonth budgetDate;
  @NotBlank
  private final String name;
  @NotNull
  private final MonetaryAmount plannedAmount;
  @NotNull
  private final Category category;
  private final String description;

  private final MonetaryAmount totalTransactions;
  private final double percentageOfPlanned;
  private final MonetaryAmount totalRemaining;

  /**
   * Unmodifiable List
   */
  private final List<Transaction> transactions;

  public LineItem(@NotNull ItemType type,
                  @NotNull YearMonth budgetDate,
                  @NotNull String name,
                  double plannedAmount,
                  @NotNull Category category) {
    this(null, type, budgetDate, name, new MonetaryAmount(plannedAmount), category, null, null, null);
  }

  @SuppressWarnings("java:S107") // SonarLint thinks this is too many constructor parameters
  public LineItem(Long id,
                  @NotNull ItemType type,
                  @NotNull YearMonth budgetDate,
                  @NotNull String name,
                  int plannedAmount,
                  @NotNull Category category,
                  String description,
                  List<Transaction> transactions,
                  Instant lastModifiedAt) {
    this(id, type, budgetDate, name, new MonetaryAmount(plannedAmount), category, description, transactions, lastModifiedAt);
  }

  @SuppressWarnings("java:S107") // SonarLint thinks this is too many constructor parameters
  public LineItem(Long id,
                  @NotNull ItemType type,
                  @NotNull YearMonth budgetDate,
                  @NotNull String name,
                  @NotNull MonetaryAmount plannedAmount,
                  @NotNull Category category,
                  String description,
                  List<Transaction> transactions,
                  Instant lastModifiedAt) {
    super(type, lastModifiedAt);
    this.id = id;
    this.budgetDate = budgetDate;
    this.name = name;
    this.plannedAmount = plannedAmount;
    this.category = category;
    this.description = description;
    this.transactions = transactions != null ? Collections.unmodifiableList(transactions) : List.of();

    this.totalTransactions = calculateTotalTransactions();
    this.percentageOfPlanned = calculatePercentageOfPlanned();
    this.totalRemaining = calculateTotalRemaining();
  }

  private MonetaryAmount calculateTotalTransactions() {
    return new MonetaryAmount(
        transactions
            .stream()
            .map(Transaction::getAmount)
            .mapToInt(MonetaryAmount::inCents)
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

  private MonetaryAmount calculateTotalRemaining() {
    return new MonetaryAmount(plannedAmount.inCents() - this.totalTransactions.inCents());
  }
}
