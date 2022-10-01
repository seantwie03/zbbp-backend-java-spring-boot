package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Getter
@ToString
public class Category extends BaseDomain {
  private final Long id;
  private final BudgetMonth budgetMonth;
  private final String name;
  /**
   * Unmodifiable List
   */
  private final List<LineItem> lineItems;

  public Category(Long id,
                  ItemType type,
                  BudgetMonth budgetMonth,
                  String name,
                  List<LineItem> lineItems,
                  Instant lastModifiedAt) {
    super(type, lastModifiedAt);
    this.id = id;
    this.budgetMonth = budgetMonth;
    this.name = name;
    this.lineItems = Collections.unmodifiableList(lineItems);
  }

  public Money calculateTotalTransactions() {
    return new Money(
        lineItems.stream()
            .map(LineItem::calculateTotalTransactions)
            .mapToInt(Money::inCents)
            .sum());
  }

  public Double calculatePercentageTransacted() {
    return lineItems.stream()
        .mapToDouble(LineItem::calculatePercentageTransacted)
        .average()
        .orElse(0.0);
  }

  public Money calculateTotalRemaining() {
    return new Money(lineItems.stream()
        .map(LineItem::calculateTotalRemaining)
        .mapToInt(Money::inCents)
        .sum());
  }

  public Double calculatePercentageRemaining() {
    return lineItems.stream()
        .mapToDouble(LineItem::calculatePercentageRemaining)
        .average()
        .orElse(0.0);
  }
}
