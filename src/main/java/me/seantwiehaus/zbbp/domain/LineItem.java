package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Getter
@ToString
@SuppressWarnings("java:S107")
public class LineItem extends BaseDomain {
  private final Long id;
  private final BudgetMonth budgetMonth;
  private final String name;
  private final Money plannedAmount;
  private final Long categoryId;
  private final String description;
  /**
   * Unmodifiable List
   */
  private final List<Transaction> transactions;

  private final Money spentAmount;
  private final Double spentPercent;
  private final Money remainingAmount;
  private final Double remainingPercent;

  public LineItem(Long id,
                  BudgetMonth budgetMonth,
                  String name,
                  Double plannedAmount,
                  Long categoryId,
                  List<Transaction> transactions,
                  Instant lastModifiedAt) {
    this(id, budgetMonth, name, new Money(plannedAmount), categoryId, null, transactions, lastModifiedAt);
  }

  public LineItem(Long id,
                  BudgetMonth budgetMonth,
                  String name,
                  Integer plannedAmount,
                  Long categoryId,
                  String description,
                  List<Transaction> transactions,
                  Instant lastModifiedAt) {
    this(id, budgetMonth, name, new Money(plannedAmount), categoryId, description, transactions, lastModifiedAt);
  }

  public LineItem(Long id,
                  BudgetMonth budgetMonth,
                  String name,
                  Money plannedAmount,
                  Long categoryId,
                  String description,
                  List<Transaction> transactions,
                  Instant lastModifiedAt) {
    super(lastModifiedAt);
    this.id = id;
    this.budgetMonth = budgetMonth;
    this.name = name;
    this.plannedAmount = plannedAmount;
    this.categoryId = categoryId;
    this.description = description;
    this.transactions = transactions != null ? Collections.unmodifiableList(transactions) : List.of();
    this.spentAmount = calculateAmountSpent();
    this.spentPercent = calculatePercentageSpentOfPlannedAmount();
    this.remainingAmount = calculateAmountRemainingOfPlannedAmount();
    this.remainingPercent = calculatePercentageRemainingOfPlannedAmount();
  }

  private Money calculateAmountSpent() {
    return new Money(
        transactions
            .stream()
            .map(Transaction::getAmount)
            .map(Money::inCents)
            .reduce(0, Integer::sum));
  }

  private Double calculatePercentageSpentOfPlannedAmount() {
    return spentAmount.inCents() * 100.0 / plannedAmount.inCents();
  }

  private Money calculateAmountRemainingOfPlannedAmount() {
    return new Money(plannedAmount.inCents() - spentAmount.inCents());
  }

  private Double calculatePercentageRemainingOfPlannedAmount() {
    return remainingAmount.inCents() * 100.0 / plannedAmount.inCents();
  }
}
