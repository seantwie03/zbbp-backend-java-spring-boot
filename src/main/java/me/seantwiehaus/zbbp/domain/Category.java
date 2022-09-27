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
  private final boolean isIncome;
  /**
   * Unmodifiable List
   */
  private final List<LineItem> lineItems;

  public Category(Long id,
                  BudgetMonth budgetMonth,
                  String name,
                  boolean isIncome,
                  List<LineItem> lineItems,
                  Instant lastModifiedAt) {
    super(lastModifiedAt);
    this.id = id;
    this.budgetMonth = budgetMonth;
    this.name = name;
    this.isIncome = isIncome;
    this.lineItems = Collections.unmodifiableList(lineItems);
  }
}
