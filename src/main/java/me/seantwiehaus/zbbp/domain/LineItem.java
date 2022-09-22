package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Getter
@ToString
public class LineItem extends BaseDomain {
  private final Long id;
  private final String name;
  private final Long categoryId;
  private final Money plannedAmount;
  private final BudgetMonth budgetMonth;
  private final Money spentAmount;
  private final Double spentPercent;
  private final Money remainingAmount;
  private final Double remainingPercent;
  /**
   * Unmodifiable List
   */
  private final List<Transaction> transactions;

  public LineItem(Instant lastModifiedAt,
                  Long id,
                  String name,
                  Long categoryId,
                  Money plannedAmount,
                  BudgetMonth budgetMonth,
                  List<Transaction> transactions) {
    super(lastModifiedAt);
    this.id = id;
    this.name = name;
    this.categoryId = categoryId;
    this.plannedAmount = plannedAmount;
    this.budgetMonth = budgetMonth;
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
